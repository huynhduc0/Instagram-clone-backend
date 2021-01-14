package com.thduc.instafake.service;

import ch.qos.logback.core.subst.Token;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.Roles;
import com.thduc.instafake.entity.Tokens;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.model.AccessTokenBody;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.RoleRepository;
import com.thduc.instafake.repository.TokenRepository;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class UserService implements UserServiceImpl{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    FollowService followService;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TokenService tokenService;

    Logger log = LoggerFactory.getLogger(UserService.class);

    public User getUserById(long id, long myId) {
      return userRepository.findById(id)
             .orElseThrow(()-> new DataNotFoundException("user","user",String.valueOf(id)));
    }
    public int checkFollowStatus(long id, long myid){
        if (id == myid) return -1;
        else
            return (followRepository.existsByFrom_IdAndTo_Id(myid,id)?1:0);
    }

    @Override
    public User addUser(User user) {
        user.setAvatar((user.getAvatar() == null)?null:FileUtils.saveFileToStorage("avatars",user.getUsername(),user.getAvatar(),true));
        user.setCover((user.getCover() == null)?null:FileUtils.saveFileToStorage("user",user.getUsername(),user.getCover(),false));
        HashSet s = new HashSet();
        s.add( roleRepository.findById(2).get());
        user.setRoles(s);
        User user1 = userRepository.save(user);
        followService.changeFollows(user1,user1);
        user1.setNumOfFollowers(1);
        user1.setNumOfFollowings(1);
        return user1;
    }

    @Override
    public Page findOtherUser(Long id, Pageable pageable) {
        return userRepository.findUsersByIdNot(id,pageable);
    }

    public List<UserRepository.UserWithFollow> findFollow(Long id, int page) {
        return userRepository.findUserWithFollowStatus(id, Constant.NUM_STEP_DATA,Constant.NUM_STEP_DATA * page);
    }

    public List<UserRepository.UserWithFollow> searchByNameFollow (Long id, int page, String username) {
        return userRepository.findUserWithFollowStatusByUsername(id,username, Constant.NUM_STEP_DATA,Constant.NUM_STEP_DATA * page);
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public User checkLogin(String u, String pass){
        User user = userRepository.findUserByUsername(u);
        if(user == null ) return null;
         return passwordEncoder.matches(pass,user.getPassword())? user: null;
    }


    public List<SimpleGrantedAuthority> getAuthority(User user) {
        User user1 = userRepository.findUserByUsername(user.getUsername());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user1.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        });
        return authorities;
    }
    public User googleLogin(AccessTokenBody body) throws IOException, GeneralSecurityException {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("213239061541-5072nudrlf2dpu7qdlvaub2vuvjg4m4f.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), body.getAccessToken());
        boolean tokenIsValid = (idToken != null) && verifier.verify(idToken);

        if (tokenIsValid) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String userId = payload.getSubject();
            User Ouser = userRepository.findBySocialId(userId);
            if(Ouser != null){
                if(body.getPushToken()!=null){
                    Set<Tokens> tokens = Ouser.getTokens();
                    Tokens token = tokenService.refreshToken(body.getPushToken(),body.getDeviceType(),body.getDeviceId());
                    if (token != null)
                        tokens.add(new Tokens(body.getPushToken(),body.getDeviceType(),body.getDeviceId()));
                    Ouser = userRepository.save(Ouser);
                }
                return Ouser;

            }else {
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                User user = new User();
                user.setAvatar(pictureUrl);
                HashSet s = new HashSet();
                s.add( roleRepository.findById(2).get());
                user.setRoles(s);
                user.setNumOfFollowers(1);
                user.setNumOfFollowings(1);
                user.setEmail(email);
                user.setFullname(familyName + " " +givenName);
                user.setUsername(email);
                user.setSocialId(userId);
                if(body.getPushToken()!=null){
                    Set<Tokens> tokens = new HashSet<>();
                    tokens.add(new Tokens(body.getPushToken(),body.getDeviceType(),body.getDeviceId()));
                    user.setTokens(tokens);
                }
                User newuser = userRepository.save(user);
                followService.changeFollows(newuser,newuser);
                return newuser;
            }
        }
         throw new BadRequestException("accessToken can not use");
    }
    public User updateUserById(Long id, User user1) {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user", "id", String.valueOf(id)));
        user.setFullname((user1.getFullname()!= null || user1.getFullname().isEmpty())?user.getFullname():user1.getFullname());
        user.setUsername((user1.getUsername()!= null || user1.getUsername().isEmpty())?user.getUsername():user1.getUsername());
        user.setAvatar((user1.getAvatar() == null)?user.getAvatar():FileUtils.saveFileToStorage("avatars",user.getUsername(),user.getAvatar(),true));
        user.setCover((user1.getCover() == null)?user.getBio():FileUtils.saveFileToStorage("user",user.getUsername(),user.getCover(),false));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("EXISTED_USERNAME");
        }
        return user;

    }
    public boolean updateToken(String pushToken,String deviceType, String deviceId,User user){
            Set<Tokens> tokens = user.getTokens();
            Tokens token = tokenService.refreshToken(pushToken,deviceType,deviceId);
            if (token != null){
                tokens.add(new Tokens(pushToken,deviceType,deviceId));
                userRepository.save(user);
            }
            return true;
    }
    public void logout(User user, String deviceId){
        Set<Tokens> currentToken = user.getTokens();
        currentToken.forEach(tokens -> {
            if (tokens.getDeviceId().equals(deviceId))
                currentToken.remove(tokens);
        });
        user.setTokens(currentToken);
        userRepository.save(user);
    }


    boolean validateUserform(User user){
        int lenUsername = user.getUsername().length();
        int lenPassword = user.getPassword().length();
        if(lenUsername > 25 || lenPassword < 6){
            return false;
        }
        return true;
    }

}

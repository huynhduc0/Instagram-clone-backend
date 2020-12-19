package com.thduc.instafake.utils;

import com.thduc.instafake.entity.HashTags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static List<String> hashTagsStringFromString(String str){
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(str);
        List<String> strs=new ArrayList<String>();
        while (mat.find()) {
            //System.out.println(mat.group(1));
            strs.add(mat.group(1));
        }
        return strs;
    }
    public static String currentTime(String upload_type){
        Random rand = new Random();
        return upload_type +"_"+ new  SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date())+"_"+String.valueOf(rand.nextInt(1000));
    }
    public static String bcriptPassword(String password){
//        Password p = new Password();
        return new BCryptPasswordEncoder().encode(password);
    }
    public static ResponseEntity Successfully(String action){
        HashMap hashMap = new HashMap();
        hashMap.put("message",action+ " successfully");
        return new ResponseEntity(hashMap,HttpStatus.OK);
    }
}

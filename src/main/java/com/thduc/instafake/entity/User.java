package com.thduc.instafake.entity;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thduc.instafake.constant.Constant;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String avatar;

    @NotBlank(message = "Username is require")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Email is require")
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is require")
    private String password;

    private int numOfFollowings;

    private int numOfFollowers;

    private String cover;

    private String bio;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;


//    @OneToMany(mappedBy="to")
//    private Set<Follows> followers;
//
//    @OneToMany(mappedBy="from")
//    private Set<Follows> following;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Roles> roles;

    public User() {
    }

    public User(long id, String avatar, @NotBlank(message = "Username is require") String username, @NotBlank(message = "Email is require") String email, @NotBlank(message = "Password is require") String password, int numOfFollowings, int numOfFollowers, String cover, String bio, Set<Roles> roles) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.email = email;
        this.password = password;
        this.numOfFollowings = numOfFollowings;
        this.numOfFollowers = numOfFollowers;
        this.cover = cover;
        this.bio = bio;
        this.roles = roles;
    }

    public User(long id, String avatar, @NotBlank(message = "Username is require") String username, @NotBlank(message = "Email is require") String email, @NotBlank(message = "Password is require") String password, int numOfFollowings, int numOfFollowers, String cover, String bio, Timestamp created, Timestamp updated, Set<Roles> roles) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.email = email;
        this.password = password;
        this.numOfFollowings = numOfFollowings;
        this.numOfFollowers = numOfFollowers;
        this.cover = cover;
        this.bio = bio;
        this.created = created;
        this.updated = updated;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumOfFollowings() {
        return numOfFollowings;
    }

    public void setNumOfFollowings(int numOfFollowings) {
        this.numOfFollowings = numOfFollowings;
    }

    public int getNumOfFollowers() {
        return numOfFollowers;
    }

    public void setNumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}

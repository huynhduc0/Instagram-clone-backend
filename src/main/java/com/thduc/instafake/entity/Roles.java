package com.thduc.instafake.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thduc.instafake.constant.Constant;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
//@JsonFilter(Constant.TBL_ROLES_FILTER)
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String rolename;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "roles")
//    private Set<User> users;

    public Roles() {
    }

    public Roles(int id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }
    public Roles( String rolename) {
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
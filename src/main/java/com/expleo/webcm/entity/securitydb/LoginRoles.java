package com.expleo.webcm.entity.securitydb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "webcm_security")
public class LoginRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_roles")
    private int id;

    @Column(name = "user_roles")
    private String roles;

    @ManyToMany(mappedBy = "roles")

    private Set<LoginUser> users = new HashSet<>();

    public LoginRoles() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<LoginUser> getUsers() {
        return users;
    }

    public void setUsers(Set<LoginUser> users) {
        this.users = users;
    }

    public void addUsers(LoginUser user){
        users.add(user);
    }

}

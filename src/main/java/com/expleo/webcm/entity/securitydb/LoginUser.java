package com.expleo.webcm.entity.securitydb;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", schema = "webcm_security")
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @Column(name = "username")
    @UniqueElements(message = "Acest user a mai fost introdus in baza de date")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "reset_token")
    private String resetToken;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "authorities",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authority")}
    )
    private List<LoginRoles> role;

    public LoginUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public List<LoginRoles> getRole() {
        return role;
    }

    public void setRole(List<LoginRoles> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", resetToken='" + resetToken + '\'' +
                '}';
    }

    public void addRoles(LoginRoles user_role) {
        role.add(user_role);
    }

    public void removeRoles(LoginRoles user_role){
        role.remove(user_role);
    }
}

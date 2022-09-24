package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public UserInfo(Long id, int version, Boolean active, String email, String firstName, String lastName, Boolean locked, String passwordHash, String role, String username) {
        this.id = id;
        this.version = version;
        this.active = active;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locked = locked;
        this.passwordHash = passwordHash;
        this.role = role;
        this.username = username;
    }

    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Long id;
    private int version;
    private Boolean active;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean locked;
    private String passwordHash;
    private String role;
    private String username;
}

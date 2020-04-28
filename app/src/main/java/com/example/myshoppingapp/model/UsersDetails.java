package com.example.myshoppingapp.model;

import java.io.Serializable;

public class UsersDetails implements Serializable {

    private String userName, userPhone, userPassword;

    public UsersDetails() {
    }

    public UsersDetails(String userName, String userPhone, String userPassword) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return userName;
    }
}

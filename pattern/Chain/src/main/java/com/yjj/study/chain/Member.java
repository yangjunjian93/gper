package com.yjj.study.chain;

public class Member {

    private String username;
    private String password;
    private String mobile;

    private String address;

    public Member(String username, String password, String mobile, String address) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

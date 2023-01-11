package com.example.q_plan;

public class Firebase_User {
    private String userId;
    private String userPw;

    public Firebase_User() {}
    public Firebase_User(String id, String pw) {
        this.userId = id;
        this.userPw = pw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw (String pw) {
        this.userPw = pw;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId = '" + userId + "\'" +
                ", userpw = '" + userPw + "\'" +
                "}";
    }
}
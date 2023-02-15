package com.example.q_plan;

import android.app.Application;
import java.util.Map;

public class Userdata extends Application {

    private String userName = "";
    private String userAge = "";
    private String userId = "";
    private String userPw = "";
    private String userEmail = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void t() {}
    public void setData(Map<String, Object> doc) {
        this.userName = doc.get("Name").toString();
        this.userAge = doc.get("Age").toString();
        this.userId = doc.get("Id").toString();
        this.userPw = doc.get("Pw").toString();
        this.userEmail = doc.get("Email").toString();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
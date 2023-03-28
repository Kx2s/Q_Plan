package com.example.q_plan;

import android.app.Application;
import android.net.Uri;

import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class Userdata extends Application {

    private String userName;
    private int userAge;
    private String userId;
    private String userPw;
    private String userEmail;
    private Uri userImage;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void setData(Map<String, Object> doc) {
        this.userName = doc.get("Name").toString();
        this.userAge = Integer.parseInt(doc.get("Age").toString());
        this.userId = doc.get("Id").toString();
        this.userPw = doc.get("Pw").toString();
        this.userEmail = doc.get("Email").toString();
    }

    //동기화
    private static Userdata instance = null;
    public static synchronized Userdata getInstance() {
        if(instance == null)
            instance = new Userdata();
        return instance;
    }

    public void changing (Map<String, Object> doc) {
        this.userName = doc.get("Name").toString();
        this.userAge = Integer.parseInt(doc.get("Age").toString());
    }

    public void setUserImage(Uri uri) { userImage = uri; }

    public Uri getUserImage() { return userImage; }

    public String getUserName() { return userName; }

    public int getUserAge() { return userAge; }

    public String getUserId() { return userId; }

    public String getUserPw() { return userPw; }

    public String getUserEmail() { return userEmail; }
}
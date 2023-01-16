package com.example.q_plan;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Firebase_User {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String userName;
    private String userAge;
    private String userId;
    private String userPw;
    private String userPw2;
    private String userEmail;

    public Firebase_User() {}
    public Firebase_User(String name, String age, String id, String pw, String pw2, String email) {
        this.userName = name;
        this.userAge = age;
        this.userId = id;
        this.userPw = pw;
        this.userPw2 = pw2;
        this.userEmail = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String age) {
        this.userAge = age;
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

    public String getUserPw2() {
        return userPw2;
    }

    public void setUserPw2 (String pw2) {
        this.userPw2 = pw2;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setuserEmail (String email) {
        this.userEmail = email;
    }


    public void sign() {
        HashMap user = new HashMap();
        user.put("Name", getUserName());
        user.put("Age", getUserAge());
        user.put("Pw", getUserPw());
        user.put("Email", getUserEmail());

        db.collection("Users").document(getUserId()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("가입성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("가입실패");
                    }
                });
    }
}
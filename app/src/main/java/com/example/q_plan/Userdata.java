package com.example.q_plan;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Userdata extends Application {

    private FirebaseFirestore db;
    private String userName;
    private int userAge;
    private String userId;
    private String userPw;
    private String userEmail;
    private Uri userImage;
    private List like;

    private Map<String, Map> json = new HashMap();

    //기본 정보 개수
    int num = 5;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void setData(Map doc) {
        this.userName = doc.get("Name").toString();
        this.userAge = Integer.parseInt(doc.get("Age").toString());
        this.userId = doc.get("Id").toString();
        this.userPw = doc.get("Pw").toString();
        this.userEmail = doc.get("Email").toString();
        this.like = (List) doc.get("like");
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

    public void setJson(Map json) {
        this.json.putAll(json);
    }

    public void setUserImage(Uri uri) { userImage = uri; }

    public Uri getUserImage() { return userImage; }

    public String getUserName() { return userName; }

    public int getUserAge() { return userAge; }

    public String getUserId() { return userId; }

    public List<String> getLike() { return like; }

    public Map<String, Map> getJson() {
        return json;
    }

    public Map<String, String> getContent (String id) {
        return json.get(id);
    }

    public void addLike(String id) {
        //이미 목록에 있다면 리턴
        if (like.contains(id))
            return;
        like.add(id);

        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            List<String> list = (List<String>) documentSnapshot.get("like");

                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(id);
                            Map update = new HashMap();
                            update.put("like", list);
                            db.collection("Users").document(userId).update(update)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Log.i("찜", "찜 성공");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("찜", "찜 실패");
                                        }
                                    });
                        }
                    }
                });
    }
    public void removeLike(String id) {
        like.remove(id);

        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            List<String> list = (List<String>) documentSnapshot.get("like");

                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.remove(id);
                            Map update = new HashMap();
                            update.put("like", list);
                            db.collection("Users").document(userId).update(update)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Log.i("찜", "찜 삭제");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("찜", "찜 삭제 실패");
                                        }
                                    });
                        }
                    }
                });
    }
}
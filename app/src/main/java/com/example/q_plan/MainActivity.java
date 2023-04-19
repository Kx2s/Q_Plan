package com.example.q_plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    
    //종혁
    private BottomNavigationView bottomNavigationView; // 하단 네비 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    //종혁

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Userdata user = Userdata.getInstance();
    public static Context mContext;
            //(Userdata) ();

    boolean rt = false;
    EditText UserId, UserPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        mContext = this;

        findViewById(R.id.Button_login).setOnClickListener(Login);
        findViewById(R.id.Button_signup).setOnClickListener(SignUp);

        UserId = findViewById(R.id.EditText_loginId);
        UserPw = findViewById(R.id.EditText_loginPw);
    }

    //로그인
    View.OnClickListener Login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String getUserId = UserId.getText().toString();
            String getUserPw = UserPw.getText().toString();

            //빈칸일시 바로 return
            if (getUserId.isEmpty() || getUserPw.isEmpty()) {
                Toast.makeText(MainActivity.this, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            //버튼 비활성화
            findViewById(R.id.Button_login).setEnabled(false);

            //데베 확인
            db.collection("Users").document(getUserId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //연결 확인
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                //아이디 값이 있다면
                                if (document.exists()) {
                                    if (document.getData().get("Pw").equals(getUserPw))
                                    {
                                        user.setData(document.getData());
                                        storageRef.child("Q_Plan/" + user.getUserId() + ".jpg")
                                                .getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        user.setUserImage(uri);
                                                        System.out.println("Success : " + uri);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        System.out.println("Failure : " + e);
                                                    }
                                                });

                                        Map tmp = new HashMap();
                                        tmp.put("category", "0");
                                        tmp.put("areaCode", "34");

                                        k_getApi qwe = new k_getApi(getApplicationContext());
                                        qwe.set(tmp);
                                        qwe.execute();

                                    } else {
                                        Toast.makeText(MainActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                        //버튼 활성화
                                        findViewById(R.id.Button_login).setEnabled(true);
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                                    //버튼 활성화
                                    findViewById(R.id.Button_login).setEnabled(true);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                            //버튼 활성화
                            findViewById(R.id.Button_login).setEnabled(true);
                        }
                    });
        }
    };

    //회원가입
    View.OnClickListener SignUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //유저의 입력값
            Intent intent = new Intent(getApplicationContext(), SignUp.class);

            startActivity(intent);
        }
    };

    //k_main 으로
    public void toMain() {
        Toast.makeText(MainActivity.this,
                "환영합니다. " + user.getUserName() + " 님", Toast.LENGTH_SHORT).show();

        //화면전환
        Intent intent = new Intent(getApplicationContext(), k_main.class);
        startActivity(intent);
    }
}
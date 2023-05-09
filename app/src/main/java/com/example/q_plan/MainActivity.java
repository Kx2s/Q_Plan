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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("data"); //BD json
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Userdata user = Userdata.getInstance();
    public static Context mContext;

    EditText UserId, UserPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        FirebaseApp.initializeApp(this);

        mContext = this;

        //버튼 설정
        findViewById(R.id.Button_login).setOnClickListener(Login);
        findViewById(R.id.Button_signup).setOnClickListener(SignUp);

        //레이아웃 설정
        UserId = findViewById(R.id.EditText_loginId);
        UserPw = findViewById(R.id.EditText_loginPw);

        //json 설정
        database.addValueEventListener(valueEventListener);
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
                                        //메인 페이지로
                                        toMain();

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

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // 데이터가 변경되었을 때 호출되는 메서드
            // dataSnapshot에서 데이터를 가져와 사용합니다.
            // 데이터를 가져오는 방법은 아래 코드를 참고하세요.

            //json db 임시저장
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                user.setJson(new HashMap() {
                    {
                        put(snapshot.child("contentid").getValue(String.class), new HashMap() {
                            {
                                put("addr", snapshot.child("addr1").getValue(String.class));
                                put("image", snapshot.child("firstimage").getValue(String.class));
                                put("x", snapshot.child("mapx").getValue(String.class));
                                put("y", snapshot.child("mapy").getValue(String.class));
                                put("title", snapshot.child("title").getValue(String.class));
                            }
                        });
                    }
                });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // 데이터를 불러오는 도중 에러가 발생했을 때 호출되는 메서드
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    };
}
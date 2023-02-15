package com.example.q_plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //종혁
    private BottomNavigationView bottomNavigationView; // 하단 네비 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private jh_Nm1 nm1;
    private jh_Nm2 nm2;
    private jh_Nm3 nm3;
    private jh_Nm4 nm4;
    //종혁

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Userdata user = new Userdata();
            //(Userdata) getApplication();

    boolean rt = false;
    EditText UserId, UserPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

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
                                        System.out.println(document.getData());
                                        user.setData(document.getData());
                                        Toast.makeText(MainActivity.this,
                                                "환영합니다. " + user.getUserName() + " 님", Toast.LENGTH_SHORT).show();
                                        toMain();
                                    } else {
                                        Toast.makeText(MainActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
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

    //Firebase 데이터 추가
    private void writeNewUser (String name, String id, String pw) {

        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("ID", id);
        user.put("PW", pw);

        db.collection("Users").document(id).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "가입을 환영합니다!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //종혁
    public void toMain() {
        setContentView(R.layout.jh_activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_nm1:
                        setFrag(0);
                        break;
                    case R.id.action_nm2:
                        setFrag(1);
                        break;
                    case R.id.action_nm3:
                        setFrag(2);
                        break;
                    case R.id.action_nm4:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        nm1 = new jh_Nm1();
        nm2 = new jh_Nm2();
        nm3 = new jh_Nm3();
        nm4 = new jh_Nm4();
        setFrag(0);  // 첫화면 지정
    }


    // 화면 전환 실행문
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, nm1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, nm2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, nm3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, nm4);
                ft.commit();
                break;
        }
    }// 종혁ㅇ
}
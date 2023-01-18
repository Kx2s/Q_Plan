package com.example.q_plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    public boolean id_check = false;
    public Firebase_User user = new Firebase_User();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //아이디 확인
        findViewById(R.id.Button_cs_idcheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText id = findViewById(R.id.EditText_cs_id);
                db.collection("Users").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot d : task.getResult()) {
                                        if (d.getId().equals(id.getText().toString())) {
                                            Toast.makeText(SignUp.this, "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                                            id_check = false;
                                            return;
                                        }
                                    }
                                    Toast.makeText(SignUp.this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    id_check = true;
                                }
                                else {
                                    Toast.makeText(SignUp.this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        findViewById(R.id.Button_cs_end).setOnClickListener(sign_end);
        findViewById(R.id.Button_signBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    View.OnClickListener sign_end = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String result = sign_check();

            if (result.equals("")) {
                Toast.makeText(SignUp.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                user.sign();
                finish();
            } else {
                Toast.makeText(SignUp.this, result + " 을(를) 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public String sign_check() {
        System.out.println("체크 시작");
        
        //이거 줄일방법 없나
        ArrayList<EditText> edit = new ArrayList<>();
        edit.add(findViewById(R.id.EditText_signName));
        edit.add(findViewById(R.id.EditText_cs_age));
        edit.add(findViewById(R.id.EditText_cs_id));
        edit.add(findViewById(R.id.EditText_cs_pw));
        edit.add(findViewById(R.id.EditText_cs_pw2));
        edit.add(findViewById(R.id.EditText_cs_email));
        System.out.println("레이아웃 등록");

        //이름
        if (edit.get(0).getText().toString().isEmpty()) //빈칸
            return "이름";
        user.setUserName(edit.get(0).getText().toString());
        System.out.println("이름 : " + user.getUserName());

        //나이
        if (edit.get(1).getText().toString().isEmpty())   //빈칸
            return "나이";
        user.setUserAge(edit.get(1).getText().toString());
        System.out.println("나이 : " + user.getUserAge());

        //아이디
        if (!id_check)
            return "아이디";
        user.setUserId(edit.get(2).getText().toString());
        System.out.println("아이디 : " + user.getUserId());


        //비번
        if (edit.get(3).getText().toString().isEmpty())
            return "비밀번호";
        else if (!edit.get(3).getText().toString().equals(edit.get(4).getText().toString()))
            return "비밀번호";

        user.setUserPw(edit.get(3).getText().toString());
        System.out.println("비밀번호 : " + user.getUserPw());

        //이메일
        Pattern p = Patterns.EMAIL_ADDRESS;
        if (p.matcher(edit.get(5).getText().toString()).matches())    //형식 확인
        {
            db.collection("Users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot d : task.getResult()) {
                                    System.out.println(d.getData().get("PW"));
                                }
                            }
                            else {
                                Toast.makeText(SignUp.this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //edit.get(7).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            return "이메일";
        }
        user.setUserEmail(edit.get(5).getText().toString());
        System.out.println("이메일 : " + user.getUserId());
        return "";
    }

}
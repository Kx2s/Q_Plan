package com.example.q_plan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    HashMap signUser = new HashMap();
    public boolean id_check = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //창현
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.Button_cs_idcheck).setOnClickListener(checkId);
        findViewById(R.id.Button_cs_end).setOnClickListener(sign_end);
        //창현
        mAuth = FirebaseAuth.getInstance();
        //창현

        //Back
        findViewById(R.id.Button_cs_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //아이디 중복 확인
    View.OnClickListener checkId = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    //회원가입 버튼
    View.OnClickListener sign_end = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String result = sign_check();

            if (result.equals("")) {
                Toast.makeText(SignUp.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                createUser(signUser.get("Email").toString(),signUser.get("Pw").toString());
                sign();
                finish();
            } else {
                Toast.makeText(SignUp.this, result + " 을(를) 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        }else{
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //입력 체크
    public String sign_check() {
        String temp;    //임시 저장
        System.out.println("체크 시작");
        
        //이거 줄일방법 없나
        ArrayList<EditText> edit = new ArrayList<>();
        edit.add(findViewById(R.id.EditText_cs_name));
        edit.add(findViewById(R.id.EditText_cs_age));
        edit.add(findViewById(R.id.EditText_cs_id));
        edit.add(findViewById(R.id.EditText_cs_pw));
        edit.add(findViewById(R.id.EditText_cs_pw2));
        edit.add(findViewById(R.id.EditText_cs_email));
        System.out.println("레이아웃 등록");


        //이름
        temp = edit.get(0).getText().toString();
        if (temp.isEmpty()) //빈칸
            return "이름";
        signUser.put("Name", temp);
        System.out.println("이름 : " + temp);


        //나이
        temp = edit.get(1).getText().toString();
        if (temp.isEmpty())   //빈칸
            return "나이";
        signUser.put("Age", temp);
        System.out.println("나이 : " + temp);


        //아이디
        temp = edit.get(2).getText().toString();
        if (!id_check)
            return "아이디";
        signUser.put("Id", temp);
        System.out.println("아이디 : " + temp);


        //비번
        temp = edit.get(3).getText().toString();
        if (temp.isEmpty())
            return "비밀번호";
        else if (!temp.equals(edit.get(4).getText().toString()))
            return "비밀번호";
        //비밀번호 6자리 이상 설정.
        else if(temp.length()<6) {
            Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요.", Toast.LENGTH_SHORT).show();
            return "비밀번호";
        }

        signUser.put("Pw", temp);
        System.out.println("비밀번호 : " + temp);


        //이메일
        temp = edit.get(5).getText().toString();
        Pattern p = Patterns.EMAIL_ADDRESS;
        if (p.matcher(temp).matches())    //형식 확인
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
            return "이메일";
        }
        signUser.put("Email", temp);
        System.out.println("이메일 : " + temp);
        return "";
    }


    //DB에 입력
    public void sign() {
        db.collection("Users").document(signUser.get("Id").toString()).set(signUser)
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
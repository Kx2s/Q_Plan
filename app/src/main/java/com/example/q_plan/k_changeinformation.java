package com.example.q_plan;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class k_changeinformation extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Userdata user = Userdata.getInstance();
    public List<EditText> edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_changeinformation);
        setting();


        findViewById(R.id.Button_changeEnd).setOnClickListener(end);
        //Back
        findViewById(R.id.Button_changeBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    View.OnClickListener end = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String temp;

            for (EditText t : edit)
                if (t.getText().toString().isEmpty()){
                    toast("빈칸을 채워주세요.");
                    return;
                }

            temp = edit.get(2).getText().toString();
            if (!temp.equals(edit.get(3).getText().toString())){
                toast("비밀번호를 다시 확인해주세요.");
                return;
            }

            HashMap change = new HashMap();

            change.put("Name", edit.get(0).getText().toString());
            change.put("Age", edit.get(1).getText().toString());

            db.collection("Users").document(user.getUserId()).update(change)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            toast("수정이 완료되었습니다.");
                            user.changing(change);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            toast("인터넷 연결을 확인해주세요.");
                        }
                    });
        }///
    };

    //사용자 정보 세팅
    void setting () {
        edit = new ArrayList<>();
        edit.add(findViewById(R.id.EditText_changeName));
        edit.add(findViewById(R.id.EditText_changeAge));
        edit.add(findViewById(R.id.EditText_changePw));
        edit.add(findViewById(R.id.EditText_changePw2));
        System.out.println("정보 수정 레이아웃 등록");

        edit.get(0).setText(user.getUserName());
        edit.get(1).setText(Integer.toString(user.getUserAge()));
    }

    //Toast 간편화
    public void toast (String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
























package com.example.q_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.id_checkbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.signupbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public boolean sign_check() {
        boolean result = true;
        //이거 줄일방법 없나
        ArrayList<EditText> edit = new ArrayList<>();
        edit.add(findViewById(R.id.signName));
        edit.add(findViewById(R.id.signBirth));
        edit.add(findViewById(R.id.signBirth2));
        edit.add(findViewById(R.id.signBirth3));
        edit.add(findViewById(R.id.signID));
        edit.add(findViewById(R.id.signPW));    //5
        edit.add(findViewById(R.id.signPW2));
        edit.add(findViewById(R.id.signmail));




        //이메일
        Pattern p = Patterns.EMAIL_ADDRESS;
        if (p.matcher(edit.get(7).getText().toString()).matches())    //형식 확인
        {

        } else {
            edit.get(7).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            result = false;
        }

        return result;
    }
}
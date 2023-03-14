package com.example.q_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


public class j_4_1 extends AppCompatActivity implements OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //j_4_1.xml 열기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_4_1);

        //'다음' 버튼 선언
        Button nextbtn=(Button) findViewById(R.id.next_open);

        //'다음' 버튼 함수
        nextbtn.setOnClickListener(this);
    }
        //'다음'버튼 클릭시 j_4_2.xml 열기
        @Override
        public void onClick(View v){
            Intent intent= new Intent(this, j_4_2.class);
            startActivity(intent);
        }
    // 창현
}

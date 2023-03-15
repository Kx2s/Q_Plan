package com.example.q_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class j_4_2 extends AppCompatActivity implements OnClickListener{

    @Override
    //j_4_2.xml열기
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_4_2);

        //'이전'버튼 선언
        Button formerbtn=(Button) findViewById(R.id.former_open);
        //'다음'버튼 선언
        Button nextbtn2=(Button) findViewById(R.id.next_open2);
        //'건너뛰기'버튼 선언
        Button jumpbtn=(Button) findViewById(R.id.jump);
        //'이전' 버튼 함수
        formerbtn.setOnClickListener(this);
        //'다음' 버튼 함수
        nextbtn2.setOnClickListener(this);
        //'건너뛰기'버튼 함수
        jumpbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            //'이전'버튼 클릭시 j_4_1.xml 열기
            case R.id.former_open:
                Intent intent= new Intent(this, j_4_1.class);
                startActivity(intent);
                break;
            //'다음'버튼 클릭시 보류
            case R.id.next_open2:
                break;
            //'건너뛰기'버튼 클릭시 테마선택 종료
            case R.id.jump:
                finish();
        }
    }
    // 창현
}

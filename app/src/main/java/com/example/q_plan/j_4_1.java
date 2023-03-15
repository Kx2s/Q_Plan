package com.example.q_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


public class j_4_1 extends AppCompatActivity implements OnClickListener{
    //j_4_1 사용자가 선택할 수 있는 버튼들
    private Button btn20, btn30,btn50,btn70,btn0,btn3,btn8,btn14,resetbtn,resetbtn2;
    //몇번 클릭했는지 수치가 나오는 텍스트
    private TextView old_T,young_T;
    private int count1=0, count2=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //j_4_1.xml 열기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_4_1);

        //'다음' 버튼 선언
        Button nextbtn=(Button) findViewById(R.id.next_open);
        //'건너뛰기'버튼 선언
        Button jumpbtn=(Button) findViewById(R.id.jump);
        //성인 버튼 선언
        btn20=findViewById(R.id.old_20);
        btn30=findViewById(R.id.old_30);
        btn50=findViewById(R.id.old_50);
        btn70=findViewById(R.id.old_70);
        resetbtn=findViewById(R.id.old_reset);

        //청소년 버튼 선언
        btn0=findViewById(R.id.old_0);
        btn3=findViewById(R.id.old_3);
        btn8=findViewById(R.id.old_8);
        btn14=findViewById(R.id.old_14);
        resetbtn2=findViewById(R.id.old_reset2);

        //성인, 아동 및 청소년 인원수 텍스트 선언
        old_T=findViewById(R.id.old);
        old_T.setText(count1+"");
        young_T=findViewById(R.id.young);
        young_T.setText(count2+"");

        //'다음' 버튼 함수
        nextbtn.setOnClickListener(this);
        //'건너뛰기'버튼 함수
        jumpbtn.setOnClickListener(this);
        //성인 버튼 함수
        btn20.setOnClickListener(this);
        btn30.setOnClickListener(this);
        btn50.setOnClickListener(this);
        btn70.setOnClickListener(this);
        resetbtn.setOnClickListener(this);

        //아동 및 청소년 버튼 함수
        btn0.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn14.setOnClickListener(this);
        resetbtn2.setOnClickListener(this);

    }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                //'다음'버튼 클릭시 j_4_2.xml 열기
                case R.id.next_open:
                    Intent intent= new Intent(this, j_4_2.class);
                    startActivity(intent);
                    break;
                //'건너뛰기'버튼 클릭시 테마선택 종료
                case R.id.jump:
                    finish();

                // 성인 버튼 클릭시 성인 숫자 1증가
                case R.id.old_20: case R.id.old_30: case R.id.old_50: case R.id.old_70:
                    count1 ++;
                    old_T.setText(count1+"");
                    break;

                // 아동 및 청소년 버튼 클릭시 아동 및 청소년 숫자 1증가
                case R.id.old_0: case R.id.old_3: case R.id.old_8: case R.id.old_14:
                    count2 ++;
                    young_T.setText(count2+"");
                    break;

                // 성인 숫자 초기화
                case R.id.old_reset:
                    count1=0;
                    old_T.setText(count1+"");
                    break;

                // 아동 및 청소년 숫자 초기화
                case R.id.old_reset2:
                    count2=0;
                    young_T.setText(count2+"");
                    break;
            }
        }
    // 창현
}

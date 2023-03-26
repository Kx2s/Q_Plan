package com.example.q_plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class j_4_4 extends AppCompatActivity implements View.OnClickListener {
    private View view;
    private w_RecycleAdapter adapter;

    //j_4_3.xml open
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_4_4);

        //이전 버튼 선언
        Button formerbtn2=(Button) findViewById(R.id.former_open2);
        //종료 버튼 선언
        Button endbtn=(Button) findViewById(R.id.end);
        //'이전' 버튼 함수
        formerbtn2.setOnClickListener(this);
        //'다음' 버튼 함수
        endbtn.setOnClickListener(this);

        //===== 데이터 생성 ===================
        List<w_Itemcard> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(new w_Itemcard(i + "번째장소", "천안시 000" + i));
        }
        //========================================================
        
        RecyclerView recyclerView = findViewById(R.id.item_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        adapter = new w_RecycleAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //종료 버튼클릭이벤트
            case R.id.end:

            //이전 버튼 클릭이벤트
            case R.id.former_open2:
                Intent intent= new Intent(this, j_4_2.class);
                startActivity(intent);
                break;
        }
    }
}

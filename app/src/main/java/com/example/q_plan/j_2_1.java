package com.example.q_plan;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kotlin.jvm.JvmOverloads;

public class j_2_1 extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_2_1);
        
        //생성하기 버튼 선언
        Button make_time2=findViewById(R.id.make_time2);
        
        //스피너 선언
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.period, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Spinner period_spinner= findViewById(R.id.spinner);
        period_spinner.setAdapter(adapter);
        String text=period_spinner.getSelectedItem().toString();
    }
}

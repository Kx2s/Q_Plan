package com.example.q_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.checkerframework.checker.units.qual.A;

import kotlin.jvm.JvmOverloads;

public class j_2_1 extends AppCompatActivity {

    public Userdata day = Userdata.getInstance();
    public String P_text;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_2_1);
        //생성하기 버튼 선언
        Button make_time2=findViewById(R.id.make_time2);


        //스피너 선언
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.period, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Spinner period_spinner= findViewById(R.id.spinner);
        period_spinner.setAdapter(adapter);

        make_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                P_text=period_spinner.getSelectedItem().toString();
                intent.putExtra("result",P_text);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

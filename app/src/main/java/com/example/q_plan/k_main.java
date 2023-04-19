package com.example.q_plan;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class k_main extends AppCompatActivity {

    //종혁
    private BottomNavigationView bottomNavigationView; // 하단 네비 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    //종혁

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w_activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_nm1:
                        setFrag(0);
                        break;
                    case R.id.action_nm2:
                        setFrag(1);
                        break;
                    case R.id.action_nm3:
                        setFrag(2);
                        break;
                    case R.id.action_nm4:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        setFrag(0);  // 첫화면 지정
    }

    // 화면 전환 실행문
    public void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, new w_Nm1());
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, new w_Nm2());
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, new w_Nm3());
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, new k_myPage());
                ft.commit();
                break;
        }
    }// 종혁ㅇ /K_class 수정
}

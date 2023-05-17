package com.example.q_plan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class k_main extends AppCompatActivity {
    //창현
    Context context;

    //종혁
    private BottomNavigationView bottomNavigationView; // 하단 네비 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    //종혁

    //창현
    PermissionListener permissionlistener=new PermissionListener(){

        @Override
        public void onPermissionGranted() {
            initView();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(context, "권한 허용을 하지 않으면 서비스를 이용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w_activity_main);
        context=this.getBaseContext();
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
                    .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다...\n [설정] > [권한] 에서 사용으로 활성화해주세요.")
                    .setPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                            //android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            //android.Manifest.permission.WRITE_EXTERNAL_STORAGE // 기기, 사진, 미디어, 파일 엑세스 권한
                    })
                    .check();

        } else {
            initView();
        }
    }

    private void initView(){
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
    }// 종혁 /K_class 수정
}

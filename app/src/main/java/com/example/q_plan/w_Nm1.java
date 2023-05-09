package com.example.q_plan;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class w_Nm1 extends Fragment implements OnMapReadyCallback{
    //구글맵 변수 선언
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    public Userdata user = Userdata.getInstance();
    private View view;
    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm1, container, false);

        //구글맵 변수 초기화
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_recyclerview);

        //Recyclerview에서의 상하로 item을 보여주는 리스트
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        List<w_Itemcard>dataList = new ArrayList<>();

        for (String key : user.getJson()) {
            System.out.println(key);
            dataList.add(new w_Itemcard(key));
        }

        //Recyclerview에서의 item 들을 출력할 형식(스타일)에 맞게 출력하기 위해 어댑터를 사용
        w_RecycleAdapter adapter = new w_RecycleAdapter(dataList, true);
        adapter.setContext(this.getContext());
        recyclerView.setAdapter(adapter);

        //spinner 선언
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.place, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Spinner place_spinner= view.findViewById(R.id.spinner2);
        place_spinner.setAdapter(adapter2);
        String text=place_spinner.getSelectedItem().toString();

        return view;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //지도 초기화 작업
        LatLng seoul = new LatLng(37.566535, 126.977969);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }
    @Override
    public void onResume() {
        super.onResume();
        // 지도 상태를 복원
        if (mMap != null) {
            mMap.clear();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // 지도 상태를 저장
        if (mMap != null) {
            mMap    .clear();
        }
    }
}

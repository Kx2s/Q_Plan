package com.example.q_plan;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class w_Nm1 extends Fragment  implements OnMapReadyCallback {
    private MapView googlemap=null;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm1, container, false);

        googlemap=(MapView)view.findViewById(R.id.map);
        googlemap.getMapAsync(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_recyclerview);

        //Recyclerview에서의 상하로 item을 보여주는 리스트
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        k_readFile f = k_readFile.getInstance();
        List<w_Itemcard>dataList = new ArrayList<>();
        for (Map o: f.show(0)) {
            dataList.add(new w_Itemcard(o.get("title").toString(),o.get("addr1").toString(), o.get("firstimage").toString()));
        }
        //Recyclerview에서의 item 들을 출력할 형식(스타일)에 맞게 출력하기 위해 어뎁터를 사용
        w_RecycleAdapter adapter = new w_RecycleAdapter(dataList, true);
        recyclerView.setAdapter(adapter);

        //spinner 선언
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.place, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Spinner place_spinner= view.findViewById(R.id.spinner2);
        place_spinner.setAdapter(adapter2);
        String text=place_spinner.getSelectedItem().toString();

        return view;
    }
    //구글 지도 관련 메소드
    @Override
    public void onStart(){
        super.onStart();
        googlemap.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        googlemap.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        googlemap.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        super.onResume();
        googlemap.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        googlemap.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googlemap.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        googlemap.onLowMemory();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(googlemap != null)
        {
            googlemap.onCreate(savedInstanceState);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        LatLng CheonAhn=new LatLng(36.909189,127.144390);

        //마커 옵션
        MarkerOptions marker = new MarkerOptions();
        marker.position(CheonAhn);
        marker.title("천안");
        marker.snippet("주요 도시");

        //맵에 마커표시, 인포윈도우 보여줌
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(CheonAhn));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

    }
}

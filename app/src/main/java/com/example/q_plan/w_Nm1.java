package com.example.q_plan;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class w_Nm1 extends Fragment implements OnMapReadyCallback{
    //구글맵 변수 선언
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    public Userdata user = Userdata.getInstance();
    private View view;
    private EditText search;
    private RecyclerView recyclerView;
    private Spinner place_spinner;
    private String place;

    List<String> dataList = new ArrayList<>();
    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm1, container, false);

        // 레이아웃 선언
        place_spinner= view.findViewById(R.id.spinner2);
        recyclerView = view.findViewById(R.id.item_recyclerview);
        search = view.findViewById(R.id.searchText);

        //spinner
        place_spinner.setAdapter(ArrayAdapter.createFromResource(
                getActivity(),R.array.place, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item));

        //이벤트
        search.setOnKeyListener(searchEvent);
        place_spinner.setOnItemSelectedListener(spinnerEvent);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        //구글맵 변수 초기화
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        // 데이터 셋팅
        setData();
        return view;
    }

    AdapterView.OnItemSelectedListener spinnerEvent = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //카테고리에 따른 구분 -> 해결못해서 그냥 노가다
            switch (adapterView.getItemAtPosition(i).toString()) {
                case "관광지" :
                    place = "12";
                    break;
                case "문화시설" :
                    place = "13";
                    break;
                case "행사" :
                    place = "15";
                    break;
                case "레포츠" :
                    place = "28";
                    break;
                case "숙박" :
                    place = "32";
                    break;
                case "쇼핑" :
                    place = "38";
                    break;
                case "음식점" :
                    place = "39";
                    break;
                default:
                    place = "0";
                    break;
            }
            Log.i("place", "place : " + place);
            setList();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    // 검색 기능
    View.OnKeyListener searchEvent = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            String str = search.getText().toString();
            Log.i("search", "검색 : " + str);
            //검색어가 없다면
            if (str == "") {
                setList();
                return false;
            }
            // Enter키를 누를때
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                List<String > data = new ArrayList();
                //모든 장소 검색
                for (Map.Entry<String, Map> entry : user.getJson().entrySet()) {
                    Map<String, String> tmp = entry.getValue();
                    String title = tmp.get("title");
                    String addr = tmp.get("addr");

                    // 검색어가 포함된다면
                    if (title.contains(str) || addr.contains(str))
                        data.add(entry.getKey());
                }
                Log.i("search", "검색 완료 : " + data.size());

                setData(data);

                return true;
            }
            return false;
        }
    };



    // 리스트 데이터 세팅
    public void setData(List<String> list) {
        dataList = new ArrayList<>(list);
        Log.i("Data", "Datalist 설정 완료");
        setList();
    }
    //기본값
    public void setData () {
        // 리스트 기본값
        List<String> list = new ArrayList<>();
        for (String key : user.getJson().keySet())
            list.add(key);

        setData(list);
    }

    // 아이템 리스트 세팅
    public void setList() {
        List<String> list = new ArrayList<>();
        // 카테고리에 따른 선별
        if (place == "0") { //전체 = "0"
            list = new ArrayList<>(dataList);
        }
        else {
            for (String id : dataList) {
                if (user.getJson().get(id).get("contenttypeid").equals(place)) {
                    list.add(id);
                }
            }
        }
        Log.i("Data", "카테고리 선별");

        //w_itemcard 형식변환
        List<w_Itemcard> itemcard = new ArrayList<>();
        for (String key : list) {
            itemcard.add(new w_Itemcard(key));
        }

        Log.i("Data", "card 형식 변환");

        //Recyclerview에서의 item 들을 출력할 형식(스타일)에 맞게 출력하기 위해 어댑터를 사용
        w_RecycleAdapter adapter = new w_RecycleAdapter(itemcard, true);
        adapter.setContext(this.getContext());
        recyclerView.setAdapter(adapter);
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

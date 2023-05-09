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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class w_Nm1 extends Fragment  implements OnMapReadyCallback{
    //구글맵 변수 선언
    private MapView googlemap=null;
    public Userdata user = Userdata.getInstance();

    private View view;
    //
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm1, container, false);

        // 위치 권한이 있는지 확인
        if(isLocationPermissionGranted()){
            //위치관리자 객체 생성
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            //위치 제공자 설정
            String locationProvider = LocationManager.GPS_PROVIDER;
            //위치 업데이트 받을 시간
            long minTime = 30 * 1000;
            //위치 업데이트 받을 최소거리
            float minDistance = 1000;
            LocationListener locationListener=new LocationListener() {
                //위치 업데이트
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };
            locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
        }else {
            // 위치 권한이 없는 경우
            requestLocationPermission();
        }


        //구글맵 변수 초기화
        googlemap=(MapView)view.findViewById(R.id.map);
        googlemap.getMapAsync(this);

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
        LatLng CheonAhn=new LatLng(36.909,127.144);

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
    //위치 권한 허용되었는지 확인
    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }
    //위치권한 요청
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), // Fragment가 속한 Activity를 가져와서 사용
                Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            new AlertDialog.Builder(requireContext()) // Fragment가 속한 Context를 가져와서 사용
                    .setTitle("Location permission needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK", (dialog, which) -> {
                        ActivityCompat.requestPermissions(
                                requireActivity(), // Fragment가 속한 Activity를 가져와서 사용
                                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE
                        );
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(), // Fragment가 속한 Activity를 가져와서 사용
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 허용된 경우
                LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
                String locationProvider = LocationManager.NETWORK_PROVIDER;
                long minTime = 60 * 1000;
                float minDistance = 1000;

            } else {
                // 위치 권한이 거부된 경우
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

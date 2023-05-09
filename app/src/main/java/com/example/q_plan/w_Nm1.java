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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class w_Nm1 extends Fragment  {
    public Userdata user = Userdata.getInstance();

    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm1, container, false);


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
}

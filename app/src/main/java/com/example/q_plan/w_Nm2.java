package com.example.q_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class w_Nm2 extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.jh_nm2, container, false);
        //장소 추천 버튼 선언
        Button btn1=view.findViewById(R.id.Newbt);
//        btn1.setOnClickListener(this);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(getActivity(), j_4_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_recyclerview2);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        List<w_Itemcard> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add(new w_Itemcard(i + "번째\n\n장소", "천안시\n\n 000" + i));
        }
        w_RecycleAdapter adapter = new w_RecycleAdapter(dataList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    //장소 버튼 클릭시 event
    //Fragment에서는 Onclick 사용 불가능. 별도의 리스너 사용.
//    public void onClick(View v){
//        Intent intent= new Intent(getActivity(), j_4_1.class);
//        startActivity(intent);
//    }
    // 창현
}

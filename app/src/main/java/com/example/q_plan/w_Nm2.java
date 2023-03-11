package com.example.q_plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class w_Nm2 extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.jh_nm2, container, false);

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
}
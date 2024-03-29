package com.example.q_plan;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class w_Nm3 extends Fragment {

    private View view;
    private w_RecycleAdapter adapter;
    public Userdata user = Userdata.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.w_nm3, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_recyclerview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<w_Itemcard> dataList = new ArrayList<>();
        for (String id : user.getLike()){
            dataList.add(new w_Itemcard(id));
        }


        adapter = new w_RecycleAdapter(dataList, false);
        adapter.setContext(this.getContext());
        recyclerView.setAdapter(adapter);


        // 드레그 기능 활상화 및 방향설정
        // TochHelper를 이요하여 item의 이동방향을 정할수 있다.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return adapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                user.removeLike(    //like 삭제
                        adapter.removeItem(viewHolder.getAdapterPosition()));   //view를 삭제하며 id 값을 반환하게 수정함
            }

            // 터치 이벤트
            @Override
            public void onSelectedChanged (@NonNull RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder){
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
}


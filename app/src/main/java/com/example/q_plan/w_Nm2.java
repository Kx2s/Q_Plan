package com.example.q_plan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class w_Nm2 extends Fragment{
    FrameLayout contentFrame;
    LayoutInflater inflater_table;
    View view;
    private Spinner schedule_Spinner;
    int day=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.j_2, container, false);
        //장소 추천 버튼 선언
        Button btn1=view.findViewById(R.id.Newbt);
        //시간표 생성 버튼 선언
        Button btn2=view.findViewById(R.id.make_time);

        //스피너 선언
        schedule_Spinner = view.findViewById(R.id.spinner3);

        schedule_Spinner.setAdapter(ArrayAdapter.createFromResource(getActivity(),R.array.schedule, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item));



        //장소 버튼 클릭시 event
        //Fragment에서는 Onclick 사용 불가능. 별도의 리스너 사용.
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(getActivity(), j_4_1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult> ()
                {
                    @Override
                    public void onActivityResult(ActivityResult data)
                    {
//                        Log.d("TAG", "data : " + data);
                        if (data.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent intent = data.getData();
                            String result = intent.getStringExtra ("result");
                            Log.v("result", "result : " + result);
                            if (result.equals("무박")){
                                btn2.setVisibility(View.GONE);
                                day=1;
                                create_table(day);
                            }
                            else if(result.equals("1박2일")){
                                btn2.setVisibility(View.GONE);
                                day=2;
                                create_table(day);
                            }
                            else if(result.equals("2박3일")){
                                btn2.setVisibility(View.GONE);
                                day=3;
                                create_table(day);
                            }
                        }
                    }
                });

        //시간표 생성 버튼 클릭시 event
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(getActivity().getBaseContext(), j_2_1.class);
                launcher.launch(intent);
            }
        });
        // 창현

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_recyclerview2);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        List<w_Itemcard> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add(new w_Itemcard(i + "번째\n\n장소", "천안시\n\n 000" + i));
        }
        w_RecycleAdapter adapter = new w_RecycleAdapter(dataList, false);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void create_table(int day){
        contentFrame=view.findViewById(R.id.time_container);
        inflater_table= (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(day==1){
            inflater_table.inflate(R.layout.j_table_1,contentFrame,true);
        }
        else if(day==2){
            inflater_table.inflate(R.layout.j_table_2,contentFrame,true);
        }
        else if(day==3){
            inflater_table.inflate(R.layout.j_table_3,contentFrame,true);
        }
    }
}

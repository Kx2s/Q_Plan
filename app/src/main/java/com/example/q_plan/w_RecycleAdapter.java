package com.example.q_plan;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class w_RecycleAdapter extends RecyclerView.Adapter<w_RecycleAdapter.ViewHolder> {

    private final List<w_Itemcard> DataList;

    public w_RecycleAdapter(List<w_Itemcard> dataList){
        DataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view);
    }
    // ↑ view Holder 를 통해서 강제로
    // BindView로 집어 넣음↓

    @Override // 데이터 setting
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        w_Itemcard item = DataList.get(position);
        holder.title.setText(item.getTitle());
        holder.contents.setText(item.getContents());
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView contents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            contents = itemView.findViewById(R.id.contents_text);
        }
    }
}
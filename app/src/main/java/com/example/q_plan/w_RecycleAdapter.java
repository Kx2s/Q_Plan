package com.example.q_plan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class w_RecycleAdapter extends RecyclerView.Adapter<w_RecycleAdapter.ViewHolder> {

    private final List<w_Itemcard> DataList;
    private View view;
    boolean bt;
    Context context;

    public w_RecycleAdapter(List<w_Itemcard> dataList, boolean bt) {
        this.DataList = dataList;
        this.bt = bt;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (bt)     //저장버튼 필요
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_itemcard2, parent, false);
        else        //필요없음
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_itemcard, parent, false);

        return new ViewHolder(view);
    }
    // ↑ view Holder 를 통해서 강제로
    // BindView로 집어 넣음↓

    @Override // 데이터 setting
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        w_Itemcard item = DataList.get(position);
        holder.title.setText(item.getTitle());
        holder.contents.setText(item.getContents());

        try {

            System.out.println(item.getImg());
            System.out.println(holder.img);

            Glide.with(context)
                    .load(item.getImg())
                    .into(holder.img);

        } catch (Exception e) {
            System.out.println("에러 : " + e);
        }

    }

    //itme 갯수릂 파악하여 count하여 itme 생성형식을지정한다.
    @Override
    public int getItemCount() {
        return DataList.size();
    }

    // Item 이동
    // item의 데이터를 from에서 to position 으로 옴긴후 form데이터를 지움
    public boolean moveItem(int fromPosition, int toPosition){
        w_Itemcard text = DataList.get(fromPosition);
        DataList.remove(fromPosition);
        DataList.add(toPosition, text);

        //Data가 변경되었다는 것을 지정함.
        notifyItemMoved(fromPosition,toPosition);
        return  true;
    }
    // Item 삭제
    public void removeItem(int position){
        DataList.remove(position);
        notifyItemRemoved(position);
    }

    //viewHolder에 데이터의 정보를 setting 한다.
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView contents;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            contents = itemView.findViewById(R.id.contents_text);
            img = itemView.findViewById(R.id.img);
        }
    }
}
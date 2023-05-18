package com.example.q_plan;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class w_RecycleAdapter extends RecyclerView.Adapter<w_RecycleAdapter.ViewHolder> {
    //인터페이스 멤버변수 추가
    private OnItemClickListener onItemClickListener;
    private final List<w_Itemcard> DataList;
    private View view;
    //저장버튼
    boolean bt;
    Context context;
    Userdata user = Userdata.getInstance();

    //인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(double latitude, double longitude);
    }

    public w_RecycleAdapter(List<w_Itemcard> dataList, boolean bt, OnItemClickListener listener) {
        this.DataList = dataList;
        this.bt = bt;
        this.onItemClickListener = listener;
    }
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
        holder.address.setText(item.getAddress());

        try {
            Glide.with(context)
                    .load(item.getImg())
                    .into(holder.img);

        } catch (Exception e) {
            Log.e("Glide 에러", e.toString());
        }

        try{
            holder.cartbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.addLike(item.getId());
                }
            });
        } catch (Exception e){}

        //창현 위치보기
        try{
            holder.position.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double lat = Double.parseDouble((String) user.getJson().get(item.getId()).get("y"));
                    double lon = Double.parseDouble((String) user.getJson().get(item.getId()).get("x"));
                    Log.v("Tag",lat + " , " + lon);
                    onItemClickListener.onItemClick(lat, lon);
                }
            });
        }catch (Exception e){}
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
    public String removeItem(int position){
        String id = DataList.get(position).getId();
        DataList.remove(position);
        notifyItemRemoved(position);

        return id;
    }

    //viewHolder에 데이터의 정보를 setting 한다.
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView address;
        ImageView img;
        //찜목록에 저장
        Button cartbt;
        //구글맵에 장소 위치보기
        Button position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            address = itemView.findViewById(R.id.address_text);
            img = itemView.findViewById(R.id.img);
            cartbt = itemView.findViewById(R.id.cartbt);
            position = itemView.findViewById(R.id.position);
        }
    }
}

package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaInfoActivity;
import com.bw.movie.mvp.model.CinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecommendCinemaListAdapter extends XRecyclerView.Adapter<RecommendCinemaListAdapter.MyHolder> {
    private Context context;
    private List<CinemaBean> list0 = new ArrayList<>();

    public RecommendCinemaListAdapter(Context context) {
        this.context = context;
    }

    public void setList0(List<CinemaBean> list0) {
        this.list0 = list0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_list_item, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        CinemaBean bean = list0.get(i);
        myHolder.logo.setImageURI(bean.getLogo());
        myHolder.name.setText(bean.getName());
        myHolder.location.setText(bean.getAddress());
        myHolder.range.setText(bean.getDistance() + "");
        if (list0.get(i).isFollowCinema()){
            myHolder.isFollow.setImageResource(R.drawable.com_icon_collection_selected);
        }else {
            myHolder.isFollow.setImageResource(R.drawable.collection_default);
        }
        // 点击跳转到影院详情页面
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"按钮",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CinemaInfoActivity.class);
                intent.putExtra("cinemaId", list0.get(i).getId());
                context.startActivity(intent);
            }
        });

        myHolder.isFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener==null){
                    Toast.makeText(context,"监听者为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 点击完毕之后关注  取消关注
                if (list0.get(i).isFollowCinema()){
                    // 取消关注
                    listener.onCancelFollow(i);
                    list0.get(i).setFollowCinema(false);
                }else {
                    // 关注
                    listener.onFollow(i);
                    list0.get(i).setFollowCinema(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list0.size();
    }

    class MyHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView logo;
        TextView name, location, range;
        ImageView isFollow;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.cinema_logo);
            name = itemView.findViewById(R.id.cinema_name);
            location = itemView.findViewById(R.id.cinema_location);
            range = itemView.findViewById(R.id.cinema_range);
            isFollow = itemView.findViewById(R.id.isFollow);
        }
    }
    // 传递接口
    private  OnFollowListener listener;
    public void setOnFollowListener(OnFollowListener listener){
        this.listener = listener;
    }
    public interface OnFollowListener{
        void onFollow(int position);
        void onCancelFollow(int position);
    }

}

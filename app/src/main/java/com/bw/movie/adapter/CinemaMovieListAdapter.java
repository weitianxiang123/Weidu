package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketDetailActivity;
import com.bw.movie.mvp.model.CinemaMovie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemaMovieListAdapter extends RecyclerView.Adapter<CinemaMovieListAdapter.MyHolder> {
    private Context context;
    private List<CinemaMovie.ResultBean> list = new ArrayList<>();

    public CinemaMovieListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CinemaMovie.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_head_move, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.image.setImageURI(list.get(i).getImageUrl());
        myHolder.name.setText(list.get(i).getName());
        myHolder.time.setText(list.get(i).getDuration());
        // 点击条目 将条目的索引传递出去
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        TextView name;
        TextView time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.frescoImage);
            name = itemView.findViewById(R.id.movieName);
            time = itemView.findViewById(R.id.movieLong);
        }
    }

    // 传递接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}

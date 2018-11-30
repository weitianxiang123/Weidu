package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CinemaMovieInfoBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CinemaMovieInfoAdapter extends XRecyclerView.Adapter<CinemaMovieInfoAdapter.MyHolder>{
    private Context context;
    private List<CinemaMovieInfoBean.ResultBean> list = new ArrayList<>();

    public CinemaMovieInfoAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CinemaMovieInfoBean.ResultBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_movie_info_item,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        CinemaMovieInfoBean.ResultBean bean = list.get(i);
        myHolder.screeningHall.setText(bean.getScreeningHall());
        myHolder.beginTime.setText(bean.getBeginTime());
        myHolder.endTime.setText(bean.getEndTime());
        myHolder.price.setText(bean.getPrice()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends XRecyclerView.ViewHolder{
        TextView screeningHall,language,beginTime,endTime,price;
        ImageView img_into;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            screeningHall = itemView.findViewById(R.id.screeningHall);
            language = itemView.findViewById(R.id.language);
            beginTime = itemView.findViewById(R.id.beginTime);
            endTime = itemView.findViewById(R.id.endTime);
            price = itemView.findViewById(R.id.price);
            img_into = itemView.findViewById(R.id.into);
        }
    }
}

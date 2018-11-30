package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.AttentionMovieBean;
import com.bw.movie.utils.DateFormatForYou;
import com.bw.movie.utils.EncryptUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：owner on 2018/11/30 11:22
 */
public class AttentionCinemaAdpter extends RecyclerView.Adapter<AttentionCinemaAdpter.Mviewhodler> {
    private Context context;
    private List<AttentionMovieBean.ResultBean> list = new ArrayList<>();

    public AttentionCinemaAdpter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AttentionCinemaAdpter.Mviewhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.attention_cinema_item, null);
        Mviewhodler mviewhodler = new Mviewhodler(inflate);
        return mviewhodler;
    }

    @Override
    public void onBindViewHolder(@NonNull AttentionCinemaAdpter.Mviewhodler mviewhodler, int i) {
        mviewhodler.mSimpleDraweeView.setImageURI(list.get(i).getImageUrl());
        mviewhodler.nameTextView.setText(list.get(i).getName());
        mviewhodler.abstractTextView.setText(list.get(i).getSummary());
        long releaseTime = list.get(i).getReleaseTime();

        try {
            String s = new DateFormatForYou().longToString(releaseTime, "yyyy-MM-dd");
            mviewhodler.timeTextView.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<AttentionMovieBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class Mviewhodler extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView nameTextView, abstractTextView, timeTextView;

        public Mviewhodler(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.attention_cinema_img);
            nameTextView = itemView.findViewById(R.id.attention_cinema_name);
            abstractTextView = itemView.findViewById(R.id.attention_cinema_abstract);
            timeTextView = itemView.findViewById(R.id.attention_cinema_time);
        }

    }
}

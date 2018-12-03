package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.AttentionCinema;
import com.bw.movie.model.AttentionMovieBean;
import com.bw.movie.utils.DateFormatForYou;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：owner on 2018/11/30 11:22
 */
public class AttentionMovieAdpter extends RecyclerView.Adapter<AttentionMovieAdpter.Mviewhodler> {
    private Context context;
    private List<AttentionCinema.ResultBean> list = new ArrayList<>();

    public AttentionMovieAdpter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AttentionMovieAdpter.Mviewhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.attention_movie_item, null);
        Mviewhodler mviewhodler = new Mviewhodler(inflate);
        return mviewhodler;
    }

    @Override
    public void onBindViewHolder(@NonNull AttentionMovieAdpter.Mviewhodler mviewhodler, int i) {
        mviewhodler.mSimpleDraweeView.setImageURI(list.get(i).getLogo());
        mviewhodler.nameTextView.setText(list.get(i).getName());
        mviewhodler.addressTextView.setText(list.get(i).getAddress());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<AttentionCinema.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class Mviewhodler extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView nameTextView, addressTextView;

        public Mviewhodler(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.attention_movie_img);
            nameTextView = itemView.findViewById(R.id.attention_movie_name);
            addressTextView = itemView.findViewById(R.id.attention_movie_address);
        }

    }
}

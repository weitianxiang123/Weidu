package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.model.MovieDetatil;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class MovieAdvandeAdapter extends RecyclerView.Adapter<MovieAdvandeAdapter.MyViewHolder> {
	private Context context;
	private List< MovieDetatil.ResultBean.ShortFilmListBean > data;

	public MovieAdvandeAdapter(Context context) {
		this.context = context;
	}

	public void setData(List<MovieDetatil.ResultBean.ShortFilmListBean> data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_movie_movie, null);
		MyViewHolder holder = new MyViewHolder(view);

		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

		myViewHolder.jzRedio.setUp(data.get(i).getVideoUrl(),"预告片"+i, Jzvd.SCREEN_WINDOW_NORMAL);
		Glide.with(context).load(data.get(i).getImageUrl()).into(myViewHolder.jzRedio.thumbImageView);

	}

	@Override
	public int getItemCount() {
		if (data==null)
		return 0;
		else
			return data.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		JzvdStd jzRedio;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			jzRedio = itemView.findViewById(R.id.movieRedio);
		}
	}
}

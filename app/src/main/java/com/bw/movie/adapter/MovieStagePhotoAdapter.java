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

public class MovieStagePhotoAdapter extends RecyclerView.Adapter<MovieStagePhotoAdapter.MyViewHolder> {
	private Context context;
	private List<String> data;

	public MovieStagePhotoAdapter(Context context) {
		this.context = context;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_stage_photo_movie, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
		Glide.with(context).load(data.get(i)).into(myViewHolder.imagePhoto);
	}

	@Override
	public int getItemCount() {
		if (data==null)
			return 0;
		return data.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		ImageView imagePhoto;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			imagePhoto= itemView.findViewById(R.id.imagePhoto);
		}
	}
}

package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 李地坤
 * @date 11/28
 * movie碎片的自条目适配器
 */
public class MovieHeadAdapter extends RecyclerView.Adapter<MovieHeadAdapter.MyViewHolder> {
    private MovieItem data;
    private Context context;

	public MovieHeadAdapter(Context context) {
		this.context = context;
	}

	public void setData(MovieItem data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_head_move, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
    myViewHolder.image.setImageURI(data.getResult().get(i).getImageUrl());
    myViewHolder.name.setText(data.getResult().get(i).getName());
    myViewHolder.time.setText("110分钟");
	}

	@Override
	public int getItemCount() {
		if (data==null)
			return 0;
		return data.getResult().size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {
		SimpleDraweeView image;
		TextView name;
		TextView time;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
	   image=itemView.findViewById(R.id.frescoImage);
	   name=itemView.findViewById(R.id.movieName);
	   time=itemView.findViewById(R.id.movieLong);
			}
	}
}

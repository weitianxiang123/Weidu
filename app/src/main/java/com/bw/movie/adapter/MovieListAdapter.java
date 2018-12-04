package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MovieDetailsActivity;
import com.bw.movie.model.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

	private Context context;
	private MovieItem data;

	public MovieListAdapter(Context context) {
		this.context = context;
	}

	public void setData(MovieItem data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_list_movie, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

		MovieItem.ResultBean bean = data.getResult().get(i);
		boolean followMovie = bean.isFollowMovie();
		String name = bean.getName();
		String imageUrl = bean.getImageUrl();
		String summary = bean.getSummary();
		if (followMovie)
		{
		myViewHolder.imageHard.setImageResource(R.drawable.com_icon_collection_default);
		}else
		{
			myViewHolder.imageHard.setImageResource(R.drawable.com_icon_collection_selected);
		}

		myViewHolder.imageSimp.setImageURI(imageUrl);
		myViewHolder.textJJ.setText(summary);
		myViewHolder.textName.setText(name);
		myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieDetailsActivity.class);
				intent.putExtra("movieId",bean.getId());
				context.startActivity(intent);
			}
		});

	}

	@Override
	public int getItemCount() {
		if (data==null)
		return 0;
		return data.getResult().size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {
		TextView textName;
		SimpleDraweeView imageSimp;
		TextView textJJ;
		ImageView imageHard;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
		  textName= itemView.findViewById(R.id.textName);
		 imageSimp= itemView.findViewById(R.id.imageSimpleDrawee);
		 textJJ=	itemView.findViewById(R.id.textJJ);
		 imageHard=	itemView.findViewById(R.id.imageHard);

		}
	}
}

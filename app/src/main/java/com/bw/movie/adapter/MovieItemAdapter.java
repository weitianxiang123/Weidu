package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MovieDetailsActivity;
import com.bw.movie.model.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 李地坤
 * @date 11/28
 * movie碎片的自条目适配器
 */
public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MyViewHolder> {
    private MovieItem data;
    private Context context;
/*    private MovieItemClickListener listener;*/


	public MovieItemAdapter(Context context) {
		this.context = context;
	}

	public void setData(MovieItem data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_item_move, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

    myViewHolder.image.setImageURI(data.getResult().get(i).getImageUrl());
    myViewHolder.name.setText(data.getResult().get(i).getName());

    //设置点击事件
		myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieDetailsActivity.class);
                     intent.putExtra("movieId",data.getResult().get(i).getId());
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
		SimpleDraweeView image;
		TextView name;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
	   image=itemView.findViewById(R.id.frescoImage);
	   name=itemView.findViewById(R.id.movieName);

		}
	}
/*	//这个适配器的条目监听
	public interface  MovieItemClickListener{
		public void onClick(int index,View view);
	}*/

/*  public void	setItemClickListener(MovieItemClickListener listener){
		this.listener=listener;
	}*/

}

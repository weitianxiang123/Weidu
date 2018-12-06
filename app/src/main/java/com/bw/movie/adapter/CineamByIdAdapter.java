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
import com.bw.movie.activity.BuyTicketDetailActivity;
import com.bw.movie.activity.CinemaInfoActivity;
import com.bw.movie.activity.CinemaSearchActivity;
import com.bw.movie.model.CinemaById;
import com.facebook.drawee.view.SimpleDraweeView;

public class CineamByIdAdapter extends RecyclerView.Adapter<CineamByIdAdapter.MyViewHolder>{
	private Context context;
	private CinemaById data;
	private int movieId;

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public CineamByIdAdapter(Context context) {
		this.context = context;
	}

	public void setData(CinemaById data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.cinema_list_item, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
		CinemaById.ResultBean bean = data.getResult().get(i);
		myViewHolder.cineamName.setText(bean.getName());
		myViewHolder.cinemaLocation.setText(bean.getAddress()+"");
		myViewHolder.cinemaLogo.setImageURI(bean.getLogo());
		myViewHolder.cinemaRange.setText(bean.getDistance()+"");

		if (bean.isFollowCinema())
		{
			myViewHolder.cinemaFollow.setImageResource(R.drawable.com_icon_collection_default);
		}else
		{
			myViewHolder.cinemaFollow.setImageResource(R.drawable.com_icon_collection_selected);
		}

         myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {

				 Intent intent = new Intent(context, BuyTicketDetailActivity.class);
				 intent.putExtra("movieId",movieId);
				 intent.putExtra("cinemaId",bean.getId());
				 intent.putExtra("cinemaName",bean.getName());
				 intent.putExtra("cinemaAddress",bean.getAddress());
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
           TextView cineamName;
           TextView cinemaLocation;
           SimpleDraweeView cinemaLogo;
           TextView cinemaRange;
           ImageView cinemaFollow;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			cineamName=itemView.findViewById(R.id.cinema_name);
            cinemaLocation=itemView.findViewById(R.id.cinema_location);
            cinemaLogo=itemView.findViewById(R.id.cinema_logo);
            cinemaRange=itemView.findViewById(R.id.cinema_range);
            cinemaFollow=itemView.findViewById(R.id.isFollow);
		}
	}
}

package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.MoviePeopleEvaluate;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Calendar;
import java.util.Date;

public class MoviePeopleEvaluateAdapter extends RecyclerView.Adapter<MoviePeopleEvaluateAdapter.MyViewHolder> {
	private Context context;
	private MoviePeopleEvaluate data;

	public MoviePeopleEvaluateAdapter(Context context) {
		this.context = context;
	}

	public void setData(MoviePeopleEvaluate data) {
		this.data = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_people_evaluate_movie, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
		MoviePeopleEvaluate.ResultBean resultBean = data.getResult().get(i);
		myViewHolder.imageHead.setImageURI(resultBean.getReplyHeadPic());
		myViewHolder.textName.setText(resultBean.getReplyUserName());
		myViewHolder.textEvaluate.setText(resultBean.getReplyContent());
		int userId=resultBean.getReplyUserId();
		Date date = Calendar.getInstance().getTime();
		long  time= date.getTime();
		 long t=time-resultBean.getReplyTime();
		int s = (int) (t / 1000);
		//秒，分，时，天，周，月，年
		int year=60*60*24*365;
        int month=60*60*24*31;
        int day=60*60*24;
        int hour=60*60;
        int minute=60;
		if (s>year)
		{
			//评论超过一年
			int evaluateYear = s / year;
			myViewHolder.textTime.setText(evaluateYear+"年前");
		}else if (s>month){
            int evaluateMonth=s/month;
			myViewHolder.textTime.setText(evaluateMonth+"月前");
		}else if(s>day){
            int evaluatDay=s/day;
			myViewHolder.textTime.setText(evaluatDay+"天前");
		}else if (s>hour){
			int evaluatHour=s/hour;
			myViewHolder.textTime.setText(evaluatHour+"小时前");
		}else if (s>minute){
			int evalutaMinute=s/minute;
			myViewHolder.textTime.setText(evalutaMinute+"分钟前");
		}else {
			myViewHolder.textTime.setText(s+"秒前");
		}


	}

	@Override
	public int getItemCount() {
		if (data==null)
		return 0;
		return data.getResult().size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {
		SimpleDraweeView imageHead;
		TextView textName;
		TextView textEvaluate;
		TextView textTime;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
		imageHead=	itemView.findViewById(R.id.imageHead);
		textName=itemView.findViewById(R.id.textName);
		textEvaluate=itemView.findViewById(R.id.textEvaluate);
		textTime=itemView.findViewById(R.id.textTime);
		}
	}
}

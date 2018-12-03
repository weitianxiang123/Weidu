package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.MovieEvaluate;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Calendar;
import java.util.Date;

public class MovieEvaluateAdapter extends RecyclerView.Adapter<MovieEvaluateAdapter.MyViewHolder> {
	private Context context;
	private MovieEvaluate data;
	private ClickEvaluateListener listener;


	public MovieEvaluateAdapter(Context context) {
		this.context = context;
	}

	public void setData(MovieEvaluate data) {
		this.data = data;
	}

	public void setListener(ClickEvaluateListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = View.inflate(context, R.layout.item_evaluate_movie, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

		MovieEvaluate.ResultBean resultBean = data.getResult().get(i);
		myViewHolder.imageHead.setImageURI(resultBean.getCommentHeadPic());
		myViewHolder.textName.setText(resultBean.getCommentUserName());
		myViewHolder.textEvaluate.setText(resultBean.getCommentContent());
		myViewHolder.imageEvalute.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.clickEvaluate(resultBean.getCommentId());
			}
		});
		int userId=resultBean.getCommentId();
		Date date = Calendar.getInstance().getTime();
		long  time= date.getTime();
		long t=time-resultBean.getCommentTime();
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
		ImageView imageEvalute;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			imageHead=	itemView.findViewById(R.id.imageHead);
			textName=itemView.findViewById(R.id.textName);
			textEvaluate=itemView.findViewById(R.id.textEvaluate);
			textTime=itemView.findViewById(R.id.textTime);
			imageEvalute=itemView.findViewById(R.id.imageEvaluate);

		}
	}


	public interface ClickEvaluateListener{
		public void clickEvaluate(int id);

	}
}

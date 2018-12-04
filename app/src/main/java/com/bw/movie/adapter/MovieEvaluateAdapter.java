package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.model.MovieEvaluate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		myViewHolder.textGreatNum.setText(resultBean.getGreatNum()+"");
		myViewHolder.textEvaluateNum.setText(resultBean.getReplyNum()+"");

		if (resultBean.getIsGreat()==0)
		{//0为否，1为是
			myViewHolder.imageGreat.setImageResource(R.drawable.com_icon_praise_default);
		}else
		{
            myViewHolder.imageGreat.setImageResource(R.drawable.com_icon_praise_selected);
		}
		myViewHolder.imageGreat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (ShareUtil.isLogin(context))
				{
					Map<String,String> fMap=new HashMap<>();
					fMap.put("commentId",resultBean.getCommentId()+"");
					new HttpHelper(context).lrHead( HttpUrl.STRING_GREAT_MOVIE,fMap,null).result(new HttpListener() {
						@Override
						public void success(String data) {

							if (data.contains("不能重复点赞"))
							{
								Toast.makeText(context, "不能重复点赞", Toast.LENGTH_SHORT).show();

							}else if (data.contains("点赞成功"))
							{
								myViewHolder.textGreatNum.setText(resultBean.getGreatNum()+1+"");
								myViewHolder.imageGreat.setImageResource(R.drawable.com_icon_praise_selected);
								Toast.makeText(context, "赞", Toast.LENGTH_SHORT).show();
							}

							//请求成功
						}

						@Override
						public void fail(String error) {
							Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
						}
					});


				}else
				{
					context.startActivity(new Intent(context, LoginActivity.class));
				}

			}
		});



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
		ImageView imageGreat;
		TextView textGreatNum;
		TextView textEvaluateNum;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			imageHead=	itemView.findViewById(R.id.imageHead);
			textName=itemView.findViewById(R.id.textName);
			textEvaluate=itemView.findViewById(R.id.textEvaluate);
			textTime=itemView.findViewById(R.id.textTime);
			imageEvalute=itemView.findViewById(R.id.imageEvaluate);
            imageGreat=itemView.findViewById(R.id.imageGreat);
			textGreatNum=itemView.findViewById(R.id.textGreatNum);
			textEvaluateNum=itemView.findViewById(R.id.textEvaluateNum);
		}
	}


	public interface ClickEvaluateListener{
		public void clickEvaluate(int id);
	}
}

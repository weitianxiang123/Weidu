package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.MovieDetatil;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MovieDetailsActivityPresenter extends AppDelegate {
	private Context context;


	TextView name1; //顶部电影详情

	ImageView hard;//关注红心

	TextView name2;//电影名称

	SimpleDraweeView imageMovie;//大图

	TextView textDetails;//详情

	TextView textAdvanceNotice;//预告

	TextView textStagePhoto;//剧照

	TextView textEvaluate;//影评

	TextView textBuy;//buy
	private MovieDetatil movieDetatil;


	@Override
	public int getLayout() {
		return R.layout.activity_movie_details;
	}

	@Override
	public void initData() {
		super.initData();



		//获取电影信息
		Activity activity=(Activity)context;
		Intent intent = activity.getIntent();
		int movieId = intent.getIntExtra("movieId", 1);

		Map<String,String> map3=new HashMap<>();
		map3.put("movieId",""+movieId);


		//关注
		getString(3, HttpUrl.STRING_ATTENTION_MOVIE,map3,true);
        //获取电影信息
		getString(1,HttpUrl.STRING_DETAIL_MOVIE,map3,true);
	}


	@Override
	public void successString(int type, String data) {
		super.successString(type, data);

    switch (type)
	{
		case 3:
			Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
			break;

		case 1:
			movieDetatil = new Gson().fromJson(data, MovieDetatil.class);
			//绘制UI
             drawPage(movieDetatil);
			break;
	}

	}


private void	drawPage(MovieDetatil movieDetatil){
		if (movieDetatil!=null)
		{
			MovieDetatil.ResultBean result = movieDetatil.getResult();
			String imageUrl = result.getImageUrl();
			String name = result.getName();
			boolean followMovie = result.isFollowMovie();

			name2.setText(name);
			imageMovie.setImageURI(imageUrl);
			if (!followMovie)//true为未关注
			{
				hard.setImageResource(R.drawable.com_icon_collection_selected);
			}else
			{
			hard.setImageResource(R.drawable.com_icon_collection_default);
			}

		}

 }

	@Override
	public void initContext(Context context) {
		super.initContext(context);
		this.context=context;
	}

	public void initView(TextView name1, ImageView hard, TextView name2, SimpleDraweeView imageMovie, TextView textDetails, TextView textAdvanceNotice, TextView textStagePhoto, TextView textEvaluate, TextView textBuy) {
         this.name1=name1;
         this.hard=hard;
         this.name2=name2;
         this.imageMovie=imageMovie;
         this.textDetails=textDetails;
         this.textAdvanceNotice=textAdvanceNotice;
         this.textStagePhoto=textStagePhoto;
         this.textEvaluate=textEvaluate;
	}



}

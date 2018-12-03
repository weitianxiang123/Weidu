package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MovieDetailsActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

public class MovieDetailsActivity extends BaseActivity<MovieDetailsActivityPresenter> {
@BindView(R.id.textMovieName)
	TextView name1; //顶部电影详情
	@BindView(R.id.imageHard)
	ImageView hard;//关注红心
	@BindView(R.id.textMovieName2)
	TextView name2;//电影名称
	@BindView(R.id.imageMovie)
	SimpleDraweeView imageMovie;//大图
	@BindView(R.id.textDetails)
	TextView textDetails;//详情
	@BindView(R.id.textAdvanceNotice)
	TextView textAdvanceNotice;//预告
	@BindView(R.id.textStagePhoto)
	TextView textStagePhoto;//剧照
	@BindView(R.id.textEvaluate)
	TextView textEvaluate;//影评
	@BindView(R.id.textBuy)
	TextView textBuy;//buy
     @BindView(R.id.layoutFather)
	RelativeLayout layoutFather;

    RecyclerView re;


	@Override
	public Class<MovieDetailsActivityPresenter> getClassDelegate() {

		return MovieDetailsActivityPresenter.class;
	}

	@Override
	public void initView() {
		super.initView();
     //反回视图
		delegate.initView(name1,hard,name2,imageMovie,textDetails,textAdvanceNotice,textStagePhoto,textEvaluate,textBuy,layoutFather);

	}

	@OnClick({R.id.imageHard,R.id.textDetails,R.id.textAdvanceNotice,R.id.textStagePhoto,R.id.textEvaluate,R.id.textBuy})
	public void onClick(View view){
		switch (view.getId())
		{
			case R.id.imageHard:
				delegate.giveHart();
				break;
			case R.id.textDetails:
				delegate.showDitailsPop();
				break;
			case R.id.textAdvanceNotice:
				delegate.showAdvancePop();
				break;
			case R.id.textStagePhoto:
				delegate.showStagephotoPop();
				break;
			case R.id.textEvaluate:
				delegate.showEvaluatePop();
				break;
			case R.id.textBuy:
				break;


		}
	}

	@Override
	public void onBackPressed() {
		if (delegate.popOnBackDown())
		{
			return;
		}

	if (Jzvd.backPress())
	{
		//按下back退出视频
		return;
	}
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//停止视频播放
	    Jzvd.releaseAllVideos();

	}
}


package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MovieListActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieListActivity extends BaseActivity<MovieListActivityPresenter> {

	@BindView(R.id.xrecycleView)
	XRecyclerView xRecyclerView;

	@BindView(R.id.textHot)
	TextView textHot;

	@BindView(R.id.textNow)
	TextView textNow;

	@BindView(R.id.textWill)
	TextView textWill;


	@Override
	public Class<MovieListActivityPresenter> getClassDelegate() {
		return MovieListActivityPresenter.class;
	}

	 @OnClick({R.id.textHot,R.id.textNow,R.id.textWill}) void onClick(View view){

		switch (view.getId())
		{
			case R.id.textHot:
				delegate.changePage(0);
				break;
			case R.id.textNow:
				delegate.changePage(1);
				break;
			case R.id.textWill:
				delegate.changePage(2);
				break;
		}


	 }



	@Override
	public void initView() {
		super.initView();

	delegate.initView(xRecyclerView,textHot,textNow,textWill);

	}
}

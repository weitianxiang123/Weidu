package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.CinemaByIdActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.w3c.dom.Text;

import butterknife.BindView;

public class CinemaByIdActivity extends BaseActivity<CinemaByIdActivityPresenter> {

	@BindView(R.id.textName)
	TextView textName;
    @BindView(R.id.recycleShow)
	XRecyclerView xRecyclerView;
	@Override
	public Class<CinemaByIdActivityPresenter> getClassDelegate() {

		return CinemaByIdActivityPresenter.class;
	}

	@Override
	public void initView() {
		super.initView();

		//获取电影Id
		//获取电影名称
		Intent intent = getIntent();
		String movieName = intent.getStringExtra("movieName");
		int movieId = intent.getIntExtra("movieId", 1);
		if (movieName!=null)

		textName.setText(movieName);
		delegate.initView(movieId,xRecyclerView);
	}
}

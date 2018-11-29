package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.CinemaInfoActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import recycler.coverflow.RecyclerCoverFlow;
/*
* 影院详情页面
* */
public class CinemaInfoActivity extends BaseActivity<CinemaInfoActivityPresenter>{
    @BindView(R.id.cinema_name)
    TextView cinemaName;
    @BindView(R.id.cinema_logo)
    SimpleDraweeView cinemaLogo;
    @BindView(R.id.cinema_location)
    TextView cinemaLocation;
    @BindView(R.id.showInfo)
    ImageView showInfo;
    @BindView(R.id.recycleRotate)
    RecyclerCoverFlow mRecyclerCoverFlow;
    @BindView(R.id.movieOptionList)
    RecyclerView mMovieOptionList;
    @Override
    public Class<CinemaInfoActivityPresenter> getClassDelegate() {
        return CinemaInfoActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        delegate.initView(cinemaName,cinemaLogo,cinemaLocation,showInfo,mRecyclerCoverFlow,mMovieOptionList,cinemaId);
    }
}

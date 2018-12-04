package com.bw.movie.activity;

import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.BuyTicketDetailActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

/*
 * 购票详情页面
 * */
public class BuyTicketDetailActivity extends BaseActivity<BuyTicketDetailActivityPresenter> {
    @BindView(R.id.cinemaName)
    TextView cinemaName;
    @BindView(R.id.cinemaLocation)
    TextView cinemaLocation;
    @BindView(R.id.movieLogo)
    SimpleDraweeView movieLogo;
    @BindView(R.id.movieName)
    TextView movieName;
    @BindView(R.id.movieType)
    TextView movieType;
    @BindView(R.id.movieDirector)
    TextView movieDirector;
    @BindView(R.id.movieDuration)
    TextView movieDuration;
    @BindView(R.id.moviePlaceOrigin)
    TextView moviePlaceOrigin;
    @BindView(R.id.xRecyclerList)
    XRecyclerView mRecyclerList;

    @Override
    public Class<BuyTicketDetailActivityPresenter> getClassDelegate() {
        return BuyTicketDetailActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        int cinemaId = getIntent().getIntExtra("cinemaId", 0);
        int movieId = getIntent().getIntExtra("movieId", 0);
        String cinemaNameTxt = getIntent().getStringExtra("cinemaName");
        String cinemaAddressTxt = getIntent().getStringExtra("cinemaAddress");
        delegate.initView(cinemaName, cinemaLocation, movieLogo, movieName, movieType, movieDirector, movieDuration, moviePlaceOrigin, mRecyclerList, cinemaId, movieId, cinemaNameTxt, cinemaAddressTxt);
    }
}

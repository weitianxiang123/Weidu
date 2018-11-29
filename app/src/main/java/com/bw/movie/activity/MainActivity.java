package com.bw.movie.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MainActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivityPresenter> {


    @BindView(R.id.cinema)
    ImageView cinema;
    @BindView(R.id.movie)
    ImageView movie;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.contentView)
    FrameLayout contentView;


    @Override
    public Class<MainActivityPresenter> getClassDelegate() {
        return MainActivityPresenter.class;
    }


    @Override
    public void initView() {
        super.initView();
        delegate.initView(cinema, movie, mine, contentView);

    }


}



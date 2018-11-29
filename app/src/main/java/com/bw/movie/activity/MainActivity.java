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
    private final int CINEMA = 1;
    private final int MOVIE = 2;
    private final int MINE = 3;

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
    @OnClick({R.id.cinema, R.id.movie, R.id.mine})
    public void click(View view) {
        // 点击图片  放大 切换图片  跳转到相应的fragment
        switch (view.getId()) {
            default:
                break;
            case R.id.cinema:
                // 影院页面
                delegate.toCinema();
                delegate.selectButton(CINEMA);
                break;
            case R.id.movie:
                // 电影页面


                delegate. toMovie();
                delegate.selectButton(MOVIE);

                break;
            case R.id.mine:
                // 我的页面
                delegate. toMine();

                delegate.selectButton(MINE);
                break;
        }
    }

}



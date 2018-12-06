package com.bw.movie.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.AttentionActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的关注
 * 张秋阳
 */
public class AttentionActivity extends BaseActivity<AttentionActivityPresenter> {
    @BindView(R.id.mine_attention_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.attention_movie)
    TextView mMovieTextView;
    @BindView(R.id.nearby_cinema)
    TextView mCinemaCinema;

    @Override
    public Class<AttentionActivityPresenter> getClassDelegate() {
        return AttentionActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.iniView(mViewPager,mMovieTextView,mCinemaCinema);
    }


    @OnClick({R.id.attention_movie, R.id.nearby_cinema})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
                //点击进入我关注的影片
            case R.id.attention_movie:
                delegate.setMovieIndex();
                break;
                //点击进入我关注的影院
            case R.id.nearby_cinema:
                delegate.setCinemaIndex();
                break;
        }
    }
}

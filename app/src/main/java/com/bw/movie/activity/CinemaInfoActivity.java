package com.bw.movie.activity;

import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.CinemaInfoActivityPresenter;

public class CinemaInfoActivity extends BaseActivity<CinemaInfoActivityPresenter>{
    @Override
    public Class<CinemaInfoActivityPresenter> getClassDelegate() {
        return CinemaInfoActivityPresenter.class;
    }
}

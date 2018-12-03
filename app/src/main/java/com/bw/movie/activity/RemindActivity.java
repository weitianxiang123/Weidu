package com.bw.movie.activity;

import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.RemindActivityPresenter;

public class RemindActivity extends BaseActivity<RemindActivityPresenter> {


    @Override
    public Class<RemindActivityPresenter> getClassDelegate() {
        return RemindActivityPresenter.class;
    }

}

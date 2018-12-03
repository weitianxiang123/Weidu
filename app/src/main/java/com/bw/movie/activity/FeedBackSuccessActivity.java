package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.FeedBackSuccessActivityPresenter;

public class FeedBackSuccessActivity extends BaseActivity<FeedBackSuccessActivityPresenter> {

    @Override
    public Class<FeedBackSuccessActivityPresenter> getClassDelegate() {
        return FeedBackSuccessActivityPresenter.class;
    }


}

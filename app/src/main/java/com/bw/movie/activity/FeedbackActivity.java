package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.FeedbackActivityPresenter;

public class FeedbackActivity extends BaseActivity<FeedbackActivityPresenter> {

    @Override
    public Class<FeedbackActivityPresenter> getClassDelegate() {
        return FeedbackActivityPresenter.class;
    }


}

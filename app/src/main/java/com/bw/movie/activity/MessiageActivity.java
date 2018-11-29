package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MessiageActivityPresenter;

public class MessiageActivity extends BaseActivity<MessiageActivityPresenter> {

    @Override
    public Class<MessiageActivityPresenter> getClassDelegate() {
        return MessiageActivityPresenter.class;
    }
}

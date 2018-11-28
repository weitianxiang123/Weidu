package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.AttentionActivityPresenter;

public class AttentionActivity extends BaseActivity<AttentionActivityPresenter> {

    @Override
    public Class<AttentionActivityPresenter> getClassDelegate() {
        return AttentionActivityPresenter.class;
    }


}

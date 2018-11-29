package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.VersionActivityPresenter;

public class VersionActivity extends BaseActivity<VersionActivityPresenter> {

    @Override
    public Class<VersionActivityPresenter> getClassDelegate() {
        return VersionActivityPresenter.class;
    }


}

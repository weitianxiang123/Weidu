package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.VersionActivityPresenter;

import butterknife.BindView;

public class VersionActivity extends BaseActivity<VersionActivityPresenter> {
    @BindView(R.id.version_code)
    TextView versionCode;
    @Override
    public Class<VersionActivityPresenter> getClassDelegate() {
        return VersionActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(versionCode);
    }
}

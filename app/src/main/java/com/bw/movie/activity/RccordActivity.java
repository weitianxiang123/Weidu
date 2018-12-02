package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.RccordActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class RccordActivity extends BaseActivity<RccordActivityPresenter> {
    @BindView(R.id.rccord_xrecyclerview)
    XRecyclerView mXRecyclerView;

    @Override
    public Class<RccordActivityPresenter> getClassDelegate() {
        return RccordActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mXRecyclerView);
    }
}

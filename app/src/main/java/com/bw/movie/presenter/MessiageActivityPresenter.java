package com.bw.movie.presenter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

/**
 * 作者：owner on 2018/11/29 08:59
 */
public class MessiageActivityPresenter extends AppDelegate {
    private Context context;

    @Override
    public int getLayout() {
        return R.layout.activity_messiage;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

    }
}

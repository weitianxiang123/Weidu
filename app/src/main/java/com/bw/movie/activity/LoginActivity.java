package com.bw.movie.activity;

import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.LoginActivityPresenter;

/**
 * 魏天祥
 * 2018/11/29
 */
public class LoginActivity extends BaseActivity<LoginActivityPresenter>{
    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }




}

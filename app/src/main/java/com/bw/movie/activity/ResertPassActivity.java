package com.bw.movie.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.ResertPassActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ResertPassActivity extends BaseActivity<ResertPassActivityPresenter> {


    @BindView(R.id.true_update)
    RelativeLayout mTrueUpdate;
    @BindView(R.id.update_oldpassword)
    EditText mUpdatePassword;
    @BindView(R.id.update_newpassword)
    EditText mUpdateNewpassword;
    @BindView(R.id.update_context_password)
    EditText mUpdateContextPassword;


    @Override
    public Class<ResertPassActivityPresenter> getClassDelegate() {
        return ResertPassActivityPresenter.class;
    }


    @Override
    public void initView() {
        super.initView();
        delegate.initView(mUpdatePassword,mUpdateNewpassword,mUpdateContextPassword);
    }

    @OnClick(R.id.true_update)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.true_update:
                delegate.getDates();
                break;
        }
    }


}

package com.bw.movie.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.LoginActivityPresenter;

import butterknife.BindView;

/**
 * 魏天祥
 * 2018/11/29
 */
public class LoginActivity extends BaseActivity<LoginActivityPresenter>{
    @BindView(R.id.btn_skip_reg)
    TextView btnskip;
    @BindView(R.id.edi_lock_password)
    EditText edi_lock_password;
    @BindView(R.id.edi_phone_name)
    EditText edi_phone_name;
    @BindView(R.id.btn_login)
    Button btn_login;
    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }
    @Override
    public void initView() {
        super.initView();
        delegate.onfindId(btnskip,edi_lock_password,edi_phone_name,btn_login);
    }
}

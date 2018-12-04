package com.bw.movie.activity;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.GuiLoginActivityPresenter;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * 魏天祥
 * 2018/12/3
 * 启动页登录界面
 */
public class GuiLoginActivity extends BaseActivity<GuiLoginActivityPresenter>{
    @BindView(R.id.edi_phone_name)
    EditText edi_phone_name;
    @BindView(R.id.edi_lock_password)
    EditText edi_lock_password;
    @BindView(R.id.btn_remember_password)
    CheckBox btn_remember_password;
    @BindView(R.id.btn_automatic_login)
    CheckBox btn_automatic_login;
    @BindView(R.id.btn_skip_reg)
    TextView btn_skip_reg;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_weixin)
    ImageView btn_weixin;

    @Override
    public Class<GuiLoginActivityPresenter> getClassDelegate() {
        return GuiLoginActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.onfindId(edi_phone_name,edi_lock_password,btn_remember_password,btn_automatic_login,btn_skip_reg,btn_login,btn_weixin);
    }


}

package com.bw.movie.activity;

import android.widget.Button;
import android.widget.EditText;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.GuiRegActivityPresenter;

import butterknife.BindView;

/**
 * 魏天祥
 * 2018/12/3
 */
public class GuiRegActivity extends BaseActivity<GuiRegActivityPresenter> {
    @BindView(R.id.edi_reg_date)
    EditText edi_reg_date;
    @BindView(R.id.edi_reg_lock)
    EditText edi_reg_lock;
    @BindView(R.id.edi_reg_mail)
    EditText edi_reg_mail;
    @BindView(R.id.edi_reg_name)
    EditText edi_reg_name;
    @BindView(R.id.edi_reg_phone)
    EditText edi_reg_phone;
    @BindView(R.id.edi_reg_sex)
    EditText edi_reg_sex;
    @BindView(R.id.btn_reg)
    Button btn_reg;
    @Override
    public Class getClassDelegate() {
        return GuiRegActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.onfindId(edi_reg_date,edi_reg_lock,edi_reg_mail,edi_reg_name,edi_reg_phone,edi_reg_sex,btn_reg);
    }
}

package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

/**
 * 魏天祥
 * 2018/11/29
 */
public class RegActivityPresenter extends AppDelegate {
    private EditText edi_reg_date,edi_reg_lock,edi_reg_mail,edi_reg_name,edi_reg_phone,edi_reg_sex;
    private Button btn_reg;
    private Context contenxt;

    @Override
    public int getLayout() {
        return R.layout.activity_reg;
    }


    public void onfindId(EditText edi_reg_date, EditText edi_reg_lock, EditText edi_reg_mail, EditText edi_reg_name, EditText edi_reg_phone, EditText edi_reg_sex, Button btn_reg) {
        this.btn_reg=btn_reg;
        this.edi_reg_date=edi_reg_date;
        this.edi_reg_lock=edi_reg_lock;
        this.edi_reg_mail=edi_reg_mail;
        this.edi_reg_name=edi_reg_name;
        this.edi_reg_phone=edi_reg_phone;
        this.edi_reg_sex=edi_reg_sex;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.contenxt=context;
    }

    @Override
    public void initData() {
        super.initData();
        String sex = edi_reg_sex.getText().toString();
        String phone = edi_reg_phone.getText().toString();
        String name = edi_reg_name.getText().toString();
        String maill = edi_reg_mail.getText().toString();
        String date = edi_reg_date.getText().toString();
        String lock = edi_reg_lock.getText().toString();
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(contenxt,"目前注册正在维护",Toast.LENGTH_LONG).show();
            }
        });

    }
}

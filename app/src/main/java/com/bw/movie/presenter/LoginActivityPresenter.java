package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.RegActivity;
import com.bw.movie.mvp.view.AppDelegate;

/**
 * 魏天祥
 * 2018/11/29
 */
public class LoginActivityPresenter extends AppDelegate{
    private Context context;
    private TextView btnskip;
    private EditText edi_lock_password;
    private EditText edi_phone_name;
    private Button btn_login;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    public void onfindId(TextView btnskip, EditText edi_lock_password, EditText edi_phone_name, Button btn_login) {
        this.btnskip=btnskip;
        this.btn_login=btn_login;
        this.edi_lock_password=edi_lock_password;
        this.edi_phone_name=edi_phone_name;
    }

    @Override
    public void initData() {
        super.initData();
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,RegActivity.class);
                context.startActivity(intent);
            }
        });
        String phone = edi_phone_name.getText().toString();
        String possword = edi_lock_password.getText().toString();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,"目前登录正在维护",Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context=context;
    }
}

package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.activity.RegActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.Base64;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
    private String phone;
    private String possword;
    private boolean isLogin;
    private RootMessage rootMessage;

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = edi_phone_name.getText().toString();
                possword = edi_lock_password.getText().toString();
                Map<String,String> map=new HashMap<>();
                map.put("phone",phone);
                map.put("pwd",EncryptUtil.encrypt(possword));
                new HttpHelper(context).lrPost(HttpUrl.STRING_LOGIN,map).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        ShareUtil.saveLogin(data,context);
                        isLogin();
                        LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                        String status = loginBean.getStatus();
                        if ("0000".equals(status)){
                            context.startActivity(new Intent(context, MessiageActivity.class));
                        }else {
                            Toast.makeText(context, ""+loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void fail(String error) {
                        Toast.makeText(context,"登录失败"+error,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public void rootMessage(boolean isLogin, RootMessage rootMessage) {
        super.rootMessage(isLogin, rootMessage);
        this.isLogin=isLogin;
        this.rootMessage=rootMessage;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context=context;
    }
}

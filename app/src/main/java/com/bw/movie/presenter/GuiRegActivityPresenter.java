package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.GuiLoginActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.EncryptUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 魏天祥
 * 2018/12/3
 */
public class GuiRegActivityPresenter extends AppDelegate {
    private EditText edi_reg_date,edi_reg_lock,edi_reg_mail,edi_reg_name,edi_reg_phone,edi_reg_sex;
    private Button btn_reg;
    private Context contenxt;
    private boolean isLogin;
    private RootMessage rootMessage;
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
    public void rootMessage(boolean isLogin, RootMessage rootMessage) {
        super.rootMessage(isLogin, rootMessage);
        this.isLogin=isLogin;
        this.rootMessage=rootMessage;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.contenxt=context;
    }

    @Override
    public void initData() {
        super.initData();
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sex = edi_reg_sex.getText().toString();
                String phone = edi_reg_phone.getText().toString();
                String name = edi_reg_name.getText().toString();
                String maill = edi_reg_mail.getText().toString();
                String date = edi_reg_date.getText().toString();
                String lock = edi_reg_lock.getText().toString();
                String decrypt = EncryptUtil.encrypt(lock);

                Map<String,String> map=new HashMap<>();
                map.put("sex",sex);
                map.put("phone",phone);
                map.put("nickName",name);
                map.put("email",maill);
                map.put("birthday",date);
                map.put("pwd",decrypt);
                map.put("pwd2",decrypt);
                map.put("imei","123456");
                map.put("ua","小米/红米");
                map.put("screenSize","5.0");
                map.put("os","android");

                new HttpHelper(contenxt).lrPost(HttpUrl.STRING_REG,map).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                        String status = loginBean.getStatus();
                        if ("0000".equals(status)){
                            contenxt.startActivity(new Intent(contenxt, GuiLoginActivity.class));
                            ((Activity)contenxt).finish();

                        }else{
                            Toast.makeText(contenxt,"注册"+loginBean.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void fail(String error) {
                        Log.i("error",error);
                    }
                });

            }
        });
    }
}

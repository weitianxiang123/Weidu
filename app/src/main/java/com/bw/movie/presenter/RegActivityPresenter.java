package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 魏天祥
 * 2018/11/29
 */
public class RegActivityPresenter extends AppDelegate {
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
                Map<String,String> map=new HashMap<>();
                map.put("sex",sex);
                map.put("phone",phone);
                map.put("name",name);
                map.put("maill",maill);
                map.put("date",date);
                map.put("lock",lock);

                new HttpHelper(contenxt).lrPost(HttpUrl.STRING_REG,map).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        ShareUtil.saveLogin(data,contenxt);
                        isLogin();
                        Toast.makeText(contenxt,"注册",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void fail(String error) {

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
}

package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
public class LoginActivityPresenter extends AppDelegate {
    private Context context;
    private TextView btnskip;
    private EditText edi_lock_password;
    private EditText edi_phone_name;
    private Button btn_login;
    private String phone;
    private String possword;
    private CheckBox btn_remember_password;
    private boolean isLogin;
    private RootMessage rootMessage;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String account;
    private String password;


    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    public void onfindId(TextView btnskip, EditText edi_lock_password, EditText edi_phone_name, Button btn_login, CheckBox btn_remember_password) {
        this.btnskip = btnskip;
        this.btn_login = btn_login;
        this.edi_lock_password = edi_lock_password;
        this.edi_phone_name = edi_phone_name;
        this.btn_remember_password = btn_remember_password;
    }

    @Override
    public void initData() {
        super.initData();
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegActivity.class);
                context.startActivity(intent);

            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isRemenber = pref.getBoolean("remember_password", false);
        if (isRemenber) {
            //将账号和密码都设置到文本中
            account = pref.getString("account", phone);
            password = pref.getString("password", possword);
            edi_phone_name.setText(account);
            edi_lock_password.setText(password);
            btn_remember_password.setChecked(true);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = edi_phone_name.getText().toString();
                possword = edi_lock_password.getText().toString();
                editor = pref.edit();

                editor.putString("account", phone);
                editor.putString("password", possword);
                if (btn_remember_password.isChecked()) {
                    editor.putBoolean("remember_password", true);
                } else {
                    editor.putBoolean("remember_password", false);
                }
                editor.apply();
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                final String encrypt = EncryptUtil.encrypt(possword);
                map.put("pwd", encrypt);
                new HttpHelper(context).lrPost(HttpUrl.STRING_LOGIN, map).result(new HttpListener() {
                    @Override
                    public void success(String data) {

                        LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                        String status = loginBean.getStatus();
                        if ("0000".equals(status)) {
                            ShareUtil.saveLogin(data, context);
                            isLogin();
//                            context.getSharedPreferences("mobileAndpwd", Context.MODE_PRIVATE)
//                                    .edit()
//                                    .putString("mobile", phone)
//                                    .putString("pwd", encrypt)
//                                    .commit();
                            context.startActivity(new Intent(context, MessiageActivity.class));
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "" + loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String error) {
                        Toast.makeText(context, "登录失败" + error, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public void rootMessage(boolean isLogin, RootMessage rootMessage) {
        super.rootMessage(isLogin, rootMessage);
        this.isLogin = isLogin;
        this.rootMessage = rootMessage;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }
}

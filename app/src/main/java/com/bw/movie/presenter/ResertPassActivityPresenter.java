package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.UpdateInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.EncryptUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：owner on 2018/12/5 14:35
 */
public class ResertPassActivityPresenter extends AppDelegate {
    private Context context;
    private Object date;
    private EditText mUpdatePassword;
    private EditText mUpdateNewpassword;
    private EditText mUpdateContextPassword;

    @Override
    public int getLayout() {
        return R.layout.activity_resert_pass;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void initView(EditText mUpdatePassword, EditText mUpdateNewpassword, EditText mUpdateContextPassword) {
        this.mUpdatePassword = mUpdatePassword;
        this.mUpdateNewpassword = mUpdateNewpassword;
        this.mUpdateContextPassword = mUpdateContextPassword;
    }

    @Override
    public void initData() {
        super.initData();

    }


    public void getDates() {

        Map<String, String> map = new HashMap<>();
        map.put("oldPwd", EncryptUtil.encrypt(mUpdatePassword.getText().toString()));
        map.put("newPwd", EncryptUtil.encrypt(mUpdateNewpassword.getText().toString()));
        map.put("newPwd2", EncryptUtil.encrypt(mUpdateContextPassword.getText().toString()));
        new HttpHelper(context).minePost(HttpUrl.STRING_UPDATA, map, null).result(new HttpListener() {
            @Override
            public void success(String data) {
                UpdateInfoBean updateInfoBean = new Gson().fromJson(data, UpdateInfoBean.class);
                if ("1001".equals(updateInfoBean.getStatus())) {
                    Toast.makeText(context, updateInfoBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, updateInfoBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                    ((Activity) context).finish();
                }
            }

            @Override
            public void fail(String error) {
                Log.i("ResertPassActivityPrese", error);
            }
        });
    }


}

package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.model.UpdateInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.DateFormatForYou;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：owner on 2018/12/3 14:36
 */
public class UpdateInfoActivityPresenter extends AppDelegate {
    private SimpleDraweeView mUpdateHead;
    private RelativeLayout mUpdateLayout;
    private EditText mUpdateName;
    private EditText mUpdateSex;
    private EditText mUpdateData;
    private EditText mUpdateMobile;
    private EditText mUpdateEmil;
    private Context context;
    private int userId;
    private String sessionId;
    private RootMessage rootMessage;
    private boolean isLogin;

    @Override
    public int getLayout() {
        return R.layout.activity_update_info;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void initView(SimpleDraweeView mUpdateHead, RelativeLayout mUpdateLayout, EditText mUpdateName, EditText mUpdateSex, EditText mUpdateData, EditText mUpdateMobile, EditText mUpdateEmil) {
        this.mUpdateHead = mUpdateHead;
        this.mUpdateLayout = mUpdateLayout;
        this.mUpdateName = mUpdateName;
        this.mUpdateSex = mUpdateSex;
        this.mUpdateData = mUpdateData;
        this.mUpdateMobile = mUpdateMobile;
        this.mUpdateEmil = mUpdateEmil;
    }

    @Override
    public void initData() {
        super.initData();

        // new DateFormatForYou().longToString(lastLoginTime, "yyyy-MM-dd")
        RootMessage.ResultBean rootMessage = ShareUtil.getRootMessage(context).getResult();
        userId = rootMessage.getUserId();
        sessionId = rootMessage.getSessionId();
        RootMessage.ResultBean.UserInfoBean userInfo = rootMessage.getUserInfo();
        initSetText(userInfo);

    }


    /**
     * 当第刚进入页面给text赋值
     *
     * @param userInfo
     */
    private void initSetText(RootMessage.ResultBean.UserInfoBean userInfo) {
        mUpdateHead.setImageURI(userInfo.getHeadPic());
        mUpdateName.setText(userInfo.getNickName());

        if (userInfo.getSex() == 1) {
            mUpdateSex.setText("男");
        } else {
            mUpdateSex.setText("女");
        }
        long lastLoginTime = userInfo.getLastLoginTime();
        try {
            mUpdateData.setText(new DateFormatForYou().longToString(lastLoginTime, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mUpdateMobile.setText(userInfo.getPhone());
    }


    public void updateInfo() {
        initUpdateText(userId, sessionId);
    }

    /**
     * 修改数据
     * 更新数据
     *
     * @param userId
     * @param sessionId
     */
    private void initUpdateText(int userId, String sessionId) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", mUpdateName.getText().toString());
        String text = mUpdateSex.getText().toString();
        if ("男".equals(text)) {
            map.put("sex", "1");
        } else if ("女".equals(text)) {
            map.put("sex", "2");
        } else {
            Toast.makeText(context, "请输入正确的性别", Toast.LENGTH_SHORT).show();
        }
        /*
        这里的put"email"因为接口问题不得不设置成死数据
         */
        map.put("email", "123@qq.com");
        //修改信息的网络请求
        new HttpHelper(context).minePost(HttpUrl.STRING_UPDATA_USER, map, headMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                UpdateInfoBean updateInfoBean = new Gson().fromJson(data, UpdateInfoBean.class);
                if ("1001".equals(updateInfoBean.getStatus())) {
                    Toast.makeText(context, updateInfoBean.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    String phone = pref.getString("account", "");
                    ;
                    String possword = pref.getString("password", "");
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phone);
                    final String encrypt = EncryptUtil.encrypt(possword);
                    map.put("pwd", encrypt);
                    /**
                     * 在修改完数据之后重新请求下, 刷新shard里面的用户数据
                     *
                     */
                    new HttpHelper(context).lrPost(HttpUrl.STRING_LOGIN, map).result(new HttpListener() {
                        @Override
                        public void success(String data) {

                            LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                            String status = loginBean.getStatus();
                            if ("0000".equals(status)) {
                                ShareUtil.saveLogin(data, context);
                                isLogin();
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

            }

            @Override
            public void fail(String error) {
            }
        });
    }

    @Override
    public void rootMessage(boolean isLogin, RootMessage rootMessage) {
        super.rootMessage(isLogin, rootMessage);
        this.isLogin = isLogin;

        this.rootMessage = rootMessage;
    }
}

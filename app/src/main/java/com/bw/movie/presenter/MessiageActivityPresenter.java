package com.bw.movie.presenter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.UpdateInfoActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.DateFormatForYou;
import com.bw.movie.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：owner on 2018/11/29 08:59
 */
public class MessiageActivityPresenter extends AppDelegate {
    private Context context;
    private SimpleDraweeView mMessageHead;
    private TextView mMessiageName;
    private TextView mMessiageSex;
    private TextView mMessiageData;
    private TextView mMessiageMobile;
    private TextView mMessiageEmil;
    private RootMessage.ResultBean.UserInfoBean userInfo;
    private TextView mUserExit;
    private Button btStartButton;
    private ObjectAnimator objectAnimator2;

    @Override
    public int getLayout() {
        return R.layout.activity_messiage;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }


    @Override
    public void initData() {
        super.initData();
        RootMessage rootData = ShareUtil.getRootMessage(context);
        userInfo = rootData.getResult().getUserInfo();
        setInfo();
    }


    public void exitUser() {
        String s = mUserExit.getText().toString();
        if ("退出".equals(s)) {
            ShareUtil.unLogin(context);
            ((Activity) context).finish();
        } else {
            Toast.makeText(context, "213", Toast.LENGTH_SHORT).show();
        }
    }

    public void initView(SimpleDraweeView mMessageHead, TextView mMessiageName, TextView mMessiageSex, TextView mMessiageData, TextView mMessiageMobile, TextView mMessiageEmil, TextView mUserExit, Button btStartButton) {
        this.mMessageHead = mMessageHead;
        this.mMessiageName = mMessiageName;
        this.mMessiageSex = mMessiageSex;
        this.mMessiageData = mMessiageData;
        this.mMessiageMobile = mMessiageMobile;
        this.mMessiageEmil = mMessiageEmil;
        this.mUserExit = mUserExit;
        this.btStartButton = btStartButton;
    }


    public void OnLongTouch() {
        objectAnimator2=ObjectAnimator.ofFloat(btStartButton,"alpha",1,0.5F,0,0.5F,1);
        objectAnimator2.setDuration(10000);
        objectAnimator2.setRepeatCount(10);
        objectAnimator2.start();
        btStartButton.setVisibility(View.VISIBLE);
        objectAnimator2.cancel();
    }

    public void startBt() {
        context.startActivity(new Intent(context, UpdateInfoActivity.class));
        btStartButton.setVisibility(View.GONE);
    }

    public void refresh() {
        showsText();
    }

    private void showsText() {
        setInfo();

    }

    private void setInfo() {
        RootMessage rootData = ShareUtil.getRootMessage(context);
        userInfo = rootData.getResult().getUserInfo();
        mMessageHead.setImageURI(userInfo.getHeadPic());
        mMessiageName.setText(userInfo.getNickName());

        if (userInfo.getSex() == 1) {
            mMessiageSex.setText("男");
        } else {
            mMessiageSex.setText("女");
        }
        long lastLoginTime = userInfo.getLastLoginTime();
        try {
            mMessiageData.setText(new DateFormatForYou().longToString(lastLoginTime, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mMessiageMobile.setText(userInfo.getPhone());
        mMessiageEmil.setText(userInfo.getEmail());
    }
}

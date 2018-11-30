package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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

    @Override
    public int getLayout() {
        return R.layout.activity_messiage;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }


    @Override
    public void initData() {
        super.initData();


        RootMessage rootData = ShareUtil.getRootMessage(context);
        userInfo = rootData.getResult().getUserInfo();

        mMessageHead.setImageURI(userInfo.getHeadPic());
        mMessiageName.setText(userInfo.getNickName());
        if (userInfo.getSex() == 1) {
            mMessiageSex.setText("男");
        } else {
            mMessiageSex.setText("女");

        }
        mMessiageData.setText(userInfo.getLastLoginTime() + "");
        mMessiageMobile.setText(userInfo.getPhone());
        //mMessiageEmil.setText(userInfo.get);
    }

    public void exitUser() {
        ShareUtil.unLogin(context);
        ((Activity) context).finish();
    }

    public void initView(SimpleDraweeView mMessageHead, TextView mMessiageName, TextView mMessiageSex, TextView mMessiageData, TextView mMessiageMobile, TextView mMessiageEmil) {
        this.mMessageHead = mMessageHead;
        this.mMessiageName = mMessiageName;
        this.mMessiageSex = mMessiageSex;
        this.mMessiageData = mMessiageData;
        this.mMessiageMobile = mMessiageMobile;
        this.mMessiageEmil = mMessiageEmil;
    }
}

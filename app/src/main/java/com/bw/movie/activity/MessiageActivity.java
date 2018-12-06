package com.bw.movie.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MessiageActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MessiageActivity extends BaseActivity<MessiageActivityPresenter> {

    @BindView(R.id.user_info)
    TextView mUserInfo;
    @BindView(R.id.user_exit)
    TextView mUserExit;
    @BindView(R.id.message_head)
    SimpleDraweeView mMessageHead;
    @BindView(R.id.head_layout)
    RelativeLayout mHeadLayout;
    @BindView(R.id.messiage_name)
    TextView mMessiageName;
    @BindView(R.id.name_layout)
    RelativeLayout mNameLayout;
    @BindView(R.id.messiage_sex)
    TextView mMessiageSex;
    @BindView(R.id.sex_layout)
    RelativeLayout mSexLayout;
    @BindView(R.id.messiage_data)
    TextView mMessiageData;
    @BindView(R.id.messiage_mobile)
    TextView mMessiageMobile;
    @BindView(R.id.messiage_emil)
    TextView mMessiageEmil;
    @BindView(R.id.messiage_info_resert)
    RelativeLayout mMessiageInfoResert;
    @BindView(R.id.update_layout)
    LinearLayout mRelativeLayout;
    @BindView(R.id.update_layout2)
    LinearLayout mRelativeLayout2;
    @BindView(R.id.update_bt_start)
    Button btStartButton;

    @Override
    public Class<MessiageActivityPresenter> getClassDelegate() {
        return MessiageActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mMessageHead, mMessiageName, mMessiageSex, mMessiageData, mMessiageMobile, mMessiageEmil, mUserExit, btStartButton);
    }

    @OnClick({R.id.user_exit,R.id.update_bt_start,R.id.messiage_info_resert})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_exit:
                //点击退出
                delegate.exitUser();
                break;
                //点击进入修改页面
            case R.id.update_bt_start:
                delegate.startBt();
                break;
                //点击进入充值密码页面
            case R.id.messiage_info_resert:
                delegate.resertPass();
                break;

        }
    }

    /**
     * 设置长按事件长按显示修改按钮
     * @param view
     * @return
     */
    @OnLongClick({R.id.update_layout,R.id.update_layout2})
    public boolean OnLongClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.update_layout:
                delegate.OnLongTouch();
                break;
            case R.id.update_layout2:
                delegate.OnLongTouch();
                break;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.refresh();
    }
}

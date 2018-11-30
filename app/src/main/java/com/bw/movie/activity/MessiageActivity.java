package com.bw.movie.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MessiageActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    public Class<MessiageActivityPresenter> getClassDelegate() {
        return MessiageActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mMessageHead,mMessiageName,mMessiageSex,mMessiageData,mMessiageMobile,mMessiageEmil);
    }

    @OnClick(R.id.user_exit)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_exit:
                delegate.exitUser();
                break;
        }
    }
}

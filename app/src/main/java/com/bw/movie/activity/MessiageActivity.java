package com.bw.movie.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MessiageActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

public class MessiageActivity extends BaseActivity<MessiageActivityPresenter> {

    @BindView(R.id.message_head)
    SimpleDraweeView mMessageHead;
    @BindView(R.id.messiage_name)
    TextView mMessiageName;
    @BindView(R.id.messiage_sex)
    TextView mMessiageSex;
    @BindView(R.id.messiage_data)
    TextView mMessiageData;
    @BindView(R.id.messiage_mobile)
    TextView mMessiageMobile;
    @BindView(R.id.messiage_emil)
    TextView mMessiageEmil;
    @BindView(R.id.user_exit)
    TextView exitTextView;

    @Override
    public Class<MessiageActivityPresenter> getClassDelegate() {
        return MessiageActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mMessageHead,mMessiageName,mMessiageSex,mMessiageData,mMessiageMobile,mMessiageEmil,exitTextView);
    }
    @OnClick({R.id.user_exit})
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

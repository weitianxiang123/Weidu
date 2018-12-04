package com.bw.movie.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.UpdateInfoActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateInfoActivity extends BaseActivity<UpdateInfoActivityPresenter> {

    @BindView(R.id.update_head)
    SimpleDraweeView mUpdateHead;
    @BindView(R.id.update_layout)
    RelativeLayout mUpdateLayout;
    @BindView(R.id.update_name)
    EditText mUpdateName;
    @BindView(R.id.update_sex)
    EditText mUpdateSex;
    @BindView(R.id.update_data)
    EditText mUpdateData;
    @BindView(R.id.update_mobile)
    EditText mUpdateMobile;
    @BindView(R.id.update_emil)
    EditText mUpdateEmil;

    @BindView(R.id.save_updata_info)
    Button mSaveUpdataInfo;

    @Override
    public Class<UpdateInfoActivityPresenter> getClassDelegate() {
        return UpdateInfoActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mUpdateHead,mUpdateLayout,mUpdateName,mUpdateSex,mUpdateData,mUpdateMobile,mUpdateEmil);
    }

    @OnClick(R.id.save_updata_info)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.save_updata_info:
                delegate.updateInfo();
                break;
        }
    }
}

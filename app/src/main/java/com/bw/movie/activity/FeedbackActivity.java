package com.bw.movie.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.FeedbackActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity<FeedbackActivityPresenter> {

    @BindView(R.id.feedback_ed)
    EditText mFeedBackEd;
    @BindView(R.id.feedback_bt)
    Button mFeedbackBt;

    @Override
    public Class<FeedbackActivityPresenter> getClassDelegate() {
        return FeedbackActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mFeedBackEd);
    }



    @OnClick(R.id.feedback_bt)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.feedback_bt:
                delegate.feedBack();
                break;
        }
    }
}

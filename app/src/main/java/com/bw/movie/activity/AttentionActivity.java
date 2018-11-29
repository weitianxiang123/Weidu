package com.bw.movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.AttentionActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class AttentionActivity extends BaseActivity<AttentionActivityPresenter> {
    @BindView(R.id.attention_xrecyclerview)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.attention_movie)
    RadioButton mmovieRadioButton;
    @BindView(R.id.attention_cinema)
    RadioButton mcinemaRadioButton;

    @Override
    public Class<AttentionActivityPresenter> getClassDelegate() {
        return AttentionActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mXRecyclerView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.attention_movie:

                break;
            case R.id.attention_cinema:

                break;
        }
    }
}

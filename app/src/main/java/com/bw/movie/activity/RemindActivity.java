package com.bw.movie.activity;

import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.RemindActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class RemindActivity extends BaseActivity<RemindActivityPresenter> {


    @BindView(R.id.remind_num)
    TextView mRemindNum;
    @BindView(R.id.remind_xrecyclerview)
    XRecyclerView mRemindXrecyclerview;

    @Override
    public Class<RemindActivityPresenter> getClassDelegate() {
        return RemindActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mRemindNum,mRemindXrecyclerview);
    }
}

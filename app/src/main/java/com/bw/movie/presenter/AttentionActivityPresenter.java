package com.bw.movie.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 作者：owner on 2018/11/28 18:47
 */
public class AttentionActivityPresenter extends AppDelegate {
    private Context context;
    private XRecyclerView mXRecyclerView;
    @Override
    public int getLayout() {
        return R.layout.activity_attention;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }
    public void initView(XRecyclerView mXRecyclerView) {
        this.mXRecyclerView = mXRecyclerView;
    }
    @Override
    public void initData() {
        super.initData();


    }

    private void doHttp() {

    }



}

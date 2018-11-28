package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

public class MainActivityPresenter extends AppDelegate{
    private Context context;
    private ImageView cinema,movie,mine;
    private FrameLayout contentView;
    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(ImageView cinema, ImageView movie, ImageView mine, FrameLayout contentView) {
       this.cinema =cinema;
       this.movie = movie;
       this.mine = mine;
       this.contentView = contentView;
    }

    @Override
    public void initData() {
        super.initData();



    }
    // 跳转方法

}

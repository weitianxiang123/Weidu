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
<<<<<<< HEAD
        test0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 测试
                test0.setText("点击");
                //张秋阳测试

            }
        });
=======

>>>>>>> 24991adc2b0858074201b5ec1e6a564ffdc64031
    }
    // 跳转方法

}

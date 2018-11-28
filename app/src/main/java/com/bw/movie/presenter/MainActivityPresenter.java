package com.bw.movie.presenter;

import android.content.Context;

import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.MineFragment;
import com.bw.movie.mvp.view.AppDelegate;

public class MainActivityPresenter extends AppDelegate {
    private Context context;
    private ImageView cinema, movie, mine;
    private FrameLayout contentView;
    private MainActivity activity;
    FragmentManager fragmentManager;
    private MineFragment mineFragment;
    private CinemaFragment cinemaFragment;

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
        this.cinema = cinema;
        this.movie = movie;
        this.mine = mine;
        this.contentView = contentView;
    }

    @Override
    public void initData() {
        super.initData();
        activity = (MainActivity) context;
        mineFragment = new MineFragment();
        cinemaFragment = new CinemaFragment();
        fragmentManager = activity.getSupportFragmentManager();


    }


    // 跳转方法
    public void toMine() {
        fragmentManager.beginTransaction().replace(R.id.contentView, mineFragment).commit();
    }

    public void toCinema() {
        fragmentManager.beginTransaction().replace(R.id.contentView,cinemaFragment).commit();
    }
}

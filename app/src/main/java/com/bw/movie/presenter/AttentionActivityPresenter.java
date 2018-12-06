package com.bw.movie.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.adapter.MovieViewPagerAdapter;
import com.bw.movie.fragment.AttentionCinemaFragment;
import com.bw.movie.fragment.AttentionMovieFragment;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.ShareUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：owner on 2018/11/28 18:47
 */
public class AttentionActivityPresenter extends AppDelegate {
    private Context context;
    private ViewPager mViewPager;
    private TextView mMovieTextView;
    private TextView mCinemaCinema;
    private List<Fragment> listFragment = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.activity_attention;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void iniView(ViewPager mViewPager, TextView mMovieTextView, TextView mCinemaCinema) {
        this.mViewPager = mViewPager;
        this.mMovieTextView = mMovieTextView;
        this.mCinemaCinema = mCinemaCinema;
    }

    @Override
    public void initData() {
        super.initData();
        //往viewpager添加两个fragment
        listFragment.add(new AttentionCinemaFragment());
        listFragment.add(new AttentionMovieFragment());
        MovieViewPagerAdapter movieViewPagerAdapter
                = new MovieViewPagerAdapter(((AttentionActivity) context).getSupportFragmentManager(), listFragment);
        mViewPager.setAdapter(movieViewPagerAdapter);
        //给viewpager设置滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    setMovieIndex();
                } else {
                    setCinemaIndex();

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    /**
     * movie页面相对应的页面操作
     */
    public void setMovieIndex() {
        mViewPager.setCurrentItem(0);
        //给电影按钮赋值
        mMovieTextView.setTextColor(Color.WHITE);
        mMovieTextView.setBackgroundResource(R.drawable.shape_attention_seleced_bg);
        //给影院按钮赋值
        mCinemaCinema.setTextColor(Color.BLACK);
        mCinemaCinema.setBackgroundResource(R.drawable.shape_attention_bg);
    }
    /**
     * Cinema页面相对应的页面操作
     */
    public void setCinemaIndex() {
        mViewPager.setCurrentItem(1);
        //给电影按钮赋值
        mMovieTextView.setTextColor(Color.BLACK);
        mMovieTextView.setBackgroundResource(R.drawable.shape_attention_bg);
        //给影院按钮赋值
        mCinemaCinema.setTextColor(Color.WHITE);
        mCinemaCinema.setBackgroundResource(R.drawable.shape_attention_seleced_bg);
    }


}

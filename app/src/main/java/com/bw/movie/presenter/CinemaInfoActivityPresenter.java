package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketDetailActivity;
import com.bw.movie.activity.CinemaInfoActivity;
import com.bw.movie.adapter.CinemaMovieInfoAdapter;
import com.bw.movie.adapter.CinemaMovieListAdapter;
import com.bw.movie.fragment.AlertCinemaDetailFragment;
import com.bw.movie.fragment.AlertCinemaRecommendFragment;
import com.bw.movie.mvp.model.CinemaInfoBean;
import com.bw.movie.mvp.model.CinemaMovie;
import com.bw.movie.mvp.model.CinemaMovieInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/*
 *影院详情页面逻辑
 * */
public class CinemaInfoActivityPresenter extends AppDelegate implements CoverFlowLayoutManger.OnSelected {
    private Context context;
    private TextView cinemaName, cinemaLocation, toDetail, toCommend;
    private RelativeLayout info_alert;
    private ViewPager info_pager;
    private SimpleDraweeView cinemaLogo;
    private ImageView showInfo, closeImg;
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private XRecyclerView mMovieOptionList;
    private int cinemaId;
    private String userId = "";
    private String sessionId = "";
    private List<CinemaMovie.ResultBean> list = new ArrayList<>();
    private List<CinemaMovieInfoBean.ResultBean> MovieInfoList = new ArrayList<>();
    private CinemaMovieListAdapter movieListAdapter;// 3D画廊适配器
    private CinemaMovieInfoAdapter movieInfoAdapter;// 下方排场适配器
    private CinemaInfoBean infoBean;
    // 弹出页2个fragment
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.activity_cinema_info;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    // 接收控件
    public void initView(TextView cinemaName, SimpleDraweeView cinemaLogo, TextView cinemaLocation, ImageView showInfo, RecyclerCoverFlow mRecyclerCoverFlow, XRecyclerView mMovieOptionList, int cinemaId, RelativeLayout info_alert, TextView toDetail, TextView toCommend, ImageView closeImg, ViewPager info_pager) {
        this.cinemaName = cinemaName;
        this.cinemaLogo = cinemaLogo;
        this.cinemaLocation = cinemaLocation;
        this.showInfo = showInfo;
        this.mRecyclerCoverFlow = mRecyclerCoverFlow;
        this.mMovieOptionList = mMovieOptionList;
        this.cinemaId = cinemaId;

        this.info_alert = info_alert;
        this.toDetail = toDetail;
        this.toCommend = toCommend;
        this.closeImg = closeImg;
        this.info_pager = info_pager;
    }

    @Override
    public void initData() {
        super.initData();
        // 获取影院ID  请求接口 展示数据    联动3D画廊  请求电影排场
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        getBean(0, HttpUrl.CINEMA_INFO, map, CinemaInfoBean.class, false);
        getBean(1, HttpUrl.MOVIE_BY_CINEMA_ID, map, CinemaMovie.class, false);
        // 创建3D画廊适配器
        movieListAdapter = new CinemaMovieListAdapter(context);
        mRecyclerCoverFlow.setAdapter(movieListAdapter);
        // 适配电影排场列表
        movieInfoAdapter = new CinemaMovieInfoAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieOptionList.setLayoutManager(manager);
        mMovieOptionList.setAdapter(movieInfoAdapter);
        // 监听3D画廊滑动
        mRecyclerCoverFlow.setOnItemSelectedListener(this);
        // 监听3D画廊条目点击
        movieListAdapter.setOnItemClickListener(new CinemaMovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int movieId = list.get(position).getId();
                Intent intent = new Intent(context, BuyTicketDetailActivity.class);
                intent.putExtra("movieId", movieId);
                intent.putExtra("cinemaId", cinemaId);
                intent.putExtra("cinemaName", infoBean.getResult().getName());
                intent.putExtra("cinemaAddress", infoBean.getResult().getAddress());
                context.startActivity(intent);
            }
        });
        // 生成fragments集合内容
        initFragments();
        // 适配弹出页内容
        info_pager.setAdapter(new FragmentPagerAdapter(((CinemaInfoActivity) context).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Fragment fragment = fragments.get(i);
                Bundle bundle = new Bundle();
                bundle.putInt("cinemaId", cinemaId);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        // 处理点击冲突
        info_alert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

    }

    private void initFragments() {
        fragments.add(new AlertCinemaDetailFragment());
        fragments.add(new AlertCinemaRecommendFragment());
    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type) {
            case 0:
                infoBean = (CinemaInfoBean) bean;
                //Toast.makeText(context,infoBean.toString(),Toast.LENGTH_SHORT).show();
                cinemaLogo.setImageURI(infoBean.getResult().getLogo());
                cinemaLocation.setText(infoBean.getResult().getAddress());
                cinemaName.setText(infoBean.getResult().getName());

                break;
            case 1:
                // 请求到了该影院即将上映电影列表  获取其中id  可以查询排期
                // 电影列表展示在3D画廊上
                CinemaMovie movie = (CinemaMovie) bean;
                list = movie.getResult();
                movieListAdapter.setList(list);
                // 拖至中间位置
                mRecyclerCoverFlow.scrollToPosition(list.size() / 2);
                break;
            case 2:
                // 请求到了电影排期信息  开始展示
                CinemaMovieInfoBean movieInfoBean = (CinemaMovieInfoBean) bean;
                //Toast.makeText(context,movieInfoBean.getResult().size()+"",Toast.LENGTH_SHORT).show();
                MovieInfoList.clear();
                MovieInfoList = movieInfoBean.getResult();
                movieInfoAdapter.setList(MovieInfoList);
                break;
        }
    }

    @Override
    public void onItemSelected(int position) {
        // 请求选中影片在该电影院的排场
        if (list.size() != 0) {
            int movieId = list.get(position).getId();
            Map<String, String> map = new HashMap<>();
            map.put("cinemasId", cinemaId + "");
            map.put("movieId", movieId + "");
            getBean(2, HttpUrl.CINEMA_MOVIE_INFO, map, CinemaMovieInfoBean.class, false);
        }

    }

    public void closeAlert() {
        // 关闭
        int height = context.getResources().getDisplayMetrics().heightPixels;
        //float y = info_alert.getY();
        // 设置动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(info_alert, "translationY", (int) 0, height);
        animator.setDuration(500);
        animator.start();
        // 彻底移除
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                info_alert.setVisibility(View.GONE);
            }
        }, 500);

    }

    public void toDetails() {
        // 前往详情
        info_pager.setCurrentItem(0);
    }

    public void toComment() {
        // 前往评论
        info_pager.setCurrentItem(1);
    }

    public void showAlert() {
        // 展示弹窗
        // 获取宽度
        int height = context.getResources().getDisplayMetrics().heightPixels;
        // 设置动画  让它从下面滑出来
        ObjectAnimator animator = ObjectAnimator.ofFloat(info_alert, "translationY", height, (float) 0);
        animator.setDuration(500);
        animator.start();
        info_alert.setVisibility(View.VISIBLE);
    }
}

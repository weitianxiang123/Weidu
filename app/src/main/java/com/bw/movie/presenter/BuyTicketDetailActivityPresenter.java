package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaMovieInfoAdapter;
import com.bw.movie.model.MovieDetatil;
import com.bw.movie.mvp.model.CinemaMovieInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 获取影院ID 电影ID 请求接口 展示电影排场 电影信息 点击条目事件 点击去购买选座页面
 * */
public class BuyTicketDetailActivityPresenter extends AppDelegate {
    private Context context;
    private TextView cinemaName, cinemaLocation, movieName, movieType, movieDirector, movieDuration, moviePlaceOrigin;
    private SimpleDraweeView movieLogo;
    private XRecyclerView mRecyclerList;
    private int cinemaId, movieId;
    private String cinemaNameTxt, cinemaAddressTxt;
    private List<CinemaMovieInfoBean.ResultBean> MovieInfoList = new ArrayList<>();// 电影排期集合
    private CinemaMovieInfoAdapter movieInfoAdapter;// 排期适配器

    @Override
    public int getLayout() {
        return R.layout.activity_buy_ticket_detail;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(TextView cinemaName, TextView cinemaLocation, SimpleDraweeView movieLogo, TextView movieName, TextView movieType, TextView movieDirector, TextView movieDuration, TextView moviePlaceOrigin, XRecyclerView mRecyclerList, int cinemaId, int movieId, String name, String cinemaAddress) {

        this.cinemaName = cinemaName;
        this.cinemaLocation = cinemaLocation;
        this.movieLogo = movieLogo;
        this.movieName = movieName;
        this.movieType = movieType;
        this.movieDirector = movieDirector;
        this.movieDuration = movieDuration;
        this.moviePlaceOrigin = moviePlaceOrigin;
        this.mRecyclerList = mRecyclerList;
        this.cinemaId = cinemaId;
        this.movieId = movieId;
        this.cinemaNameTxt = name;
        this.cinemaAddressTxt = cinemaAddress;
    }

    @Override
    public void initData() {
        super.initData();
        // 影院名 地址赋值
        cinemaName.setText(cinemaNameTxt);
        cinemaLocation.setText(cinemaAddressTxt);
        // 适配
        movieInfoAdapter = new CinemaMovieInfoAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerList.setLayoutManager(manager);
        mRecyclerList.setAdapter(movieInfoAdapter);

        // 请求电影详情
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movieId + "");
        getBean(0, HttpUrl.STRING_DETAIL_MOVIE, map, MovieDetatil.class, false);// 这里需要改为true
        // 请求电影在该影院的排场
        Map<String, String> map2 = new HashMap<>();
        map2.put("movieId", movieId + "");
        map2.put("cinemasId", cinemaId + "");
        getBean(1, HttpUrl.CINEMA_MOVIE_INFO, map2, CinemaMovieInfoBean.class, false);

    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type) {
            case 0:
                // 请求到了电影详情 给控件赋值
                MovieDetatil movieBean = (MovieDetatil) bean;
                movieLogo.setImageURI(movieBean.getResult().getImageUrl());
                movieName.setText(movieBean.getResult().getName());
                movieType.setText(movieBean.getResult().getMovieTypes());
                movieDirector.setText(movieBean.getResult().getDirector());
                movieDuration.setText(movieBean.getResult().getDuration());
                moviePlaceOrigin.setText(movieBean.getResult().getPlaceOrigin());
                break;
            case 1:
                // 请求到了该影院该电影排期 适配在下放列表中
                CinemaMovieInfoBean movieInfoBean = (CinemaMovieInfoBean) bean;
                //Toast.makeText(context,movieInfoBean.getResult().size()+"",Toast.LENGTH_SHORT).show();
                MovieInfoList.clear();
                MovieInfoList = movieInfoBean.getResult();
                movieInfoAdapter.setList(MovieInfoList);
                break;
        }
    }
}

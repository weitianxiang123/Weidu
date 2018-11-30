package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaMovieInfoAdapter;
import com.bw.movie.adapter.CinemaMovieListAdapter;
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
    private TextView cinemaName,cinemaLocation;
    private SimpleDraweeView cinemaLogo;
    private ImageView showInfo;
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private XRecyclerView mMovieOptionList;
    private int cinemaId;
    private String userId = "";
    private String sessionId = "";
    private List<CinemaMovie.ResultBean> list = new ArrayList<>();
    private List<CinemaMovieInfoBean.ResultBean> MovieInfoList = new ArrayList<>();
    private CinemaMovieListAdapter movieListAdapter;
    private CinemaMovieInfoAdapter movieInfoAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_cinema_info;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(TextView cinemaName, SimpleDraweeView cinemaLogo, TextView cinemaLocation, ImageView showInfo, RecyclerCoverFlow mRecyclerCoverFlow, XRecyclerView mMovieOptionList, int cinemaId) {
        this.cinemaName = cinemaName;
        this.cinemaLogo = cinemaLogo;
        this.cinemaLocation = cinemaLocation;
        this.showInfo = showInfo;
        this.mRecyclerCoverFlow = mRecyclerCoverFlow;
        this.mMovieOptionList = mMovieOptionList;
        this.cinemaId = cinemaId;
    }

    @Override
    public void initData() {
        super.initData();
        // 获取影院ID  请求接口 展示数据    联动3D画廊  请求电影排场
        Map<String,String> map = new HashMap<>();
        map.put("cinemaId",cinemaId+"");
        getBean(0, HttpUrl.CINEMA_INFO,map, CinemaInfoBean.class,false);
        getBean(1,HttpUrl.MOVIE_BY_CINEMA_ID,map, CinemaMovie.class,false);
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
    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type){
            case 0:
                CinemaInfoBean infoBean = (CinemaInfoBean) bean;
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
                mRecyclerCoverFlow.scrollToPosition(list.size()/2);
                break;
            case 2:
                // 请求到了电影排期信息  开始展示
                CinemaMovieInfoBean movieInfoBean = (CinemaMovieInfoBean) bean;
                //Toast.makeText(context,movieInfoBean.getResult().size()+"",Toast.LENGTH_SHORT).show();
                MovieInfoList = movieInfoBean.getResult();
                movieInfoAdapter.setList(MovieInfoList);
                break;
        }
    }

    @Override
    public void onItemSelected(int position) {
        // 请求选中影片在该电影院的排场
        if (list.size()!=0){
            int movieId = list.get(position).getId();
            Map<String, String> map = new HashMap<>();
            map.put("cinemasId",cinemaId+"");
            map.put("movieId",movieId+"");
            getBean(2,HttpUrl.CINEMA_MOVIE_INFO,map, CinemaMovieInfoBean.class,false);
        }

    }
}

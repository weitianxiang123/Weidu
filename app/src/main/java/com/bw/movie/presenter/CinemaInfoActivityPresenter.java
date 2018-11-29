package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CinemaInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import recycler.coverflow.RecyclerCoverFlow;

/*
 *影院详情页面逻辑
 * */
public class CinemaInfoActivityPresenter extends AppDelegate {
    private Context context;
    private TextView cinemaName,cinemaLocation;
    private SimpleDraweeView cinemaLogo;
    private ImageView showInfo;
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private RecyclerView mMovieOptionList;
    private int cinemaId;
    private String userId = "";
    private String sessionId = "";

    @Override
    public int getLayout() {
        return R.layout.activity_cinema_info;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(TextView cinemaName, SimpleDraweeView cinemaLogo, TextView cinemaLocation, ImageView showInfo, RecyclerCoverFlow mRecyclerCoverFlow, RecyclerView mMovieOptionList, int cinemaId) {
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
    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type){
            case 0:
                CinemaInfoBean infoBean = (CinemaInfoBean) bean;
                Toast.makeText(context,infoBean.toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

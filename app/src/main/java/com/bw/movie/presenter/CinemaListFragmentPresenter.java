package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.adapter.RecommendCinemaListAdapter;
import com.bw.movie.mvp.model.CinemaBean;
import com.bw.movie.mvp.model.RecommendCinemaBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaListFragmentPresenter extends AppDelegate{
    private Context context;
    private XRecyclerView mCinemaList;
    private RecommendCinemaListAdapter cinemaListAdapter;
    private int page = 1;
    private List<CinemaBean> list = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment_cinema_list;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(XRecyclerView mCinemaList) {
        this.mCinemaList = mCinemaList;
    }

    @Override
    public void initData() {
        super.initData();
        // 请求接口数据
        Map<String,String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("count",10+"");
        getBean(0, HttpUrl.RECOMMEND_CINEMA,map, RecommendCinemaBean.class,false);
        // 适配
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mCinemaList.setLayoutManager(manager);
        cinemaListAdapter = new RecommendCinemaListAdapter(context);
        mCinemaList.setAdapter(cinemaListAdapter);
    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type){
            case 0:
                RecommendCinemaBean cinemaBean = (RecommendCinemaBean) bean;
                //Log.i("bean",cinemaBean.getMessage());
                List<RecommendCinemaBean.ResultBean.FollowCinemasBean> followCinemas = cinemaBean.getResult().getFollowCinemas();
                List<RecommendCinemaBean.ResultBean.NearbyCinemaListBean> nearbyCinemaList = cinemaBean.getResult().getNearbyCinemaList();
                cinemaListAdapter.setList0(nearbyCinemaList);

                break;
        }
    }
}

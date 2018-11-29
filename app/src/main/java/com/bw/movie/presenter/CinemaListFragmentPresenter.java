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

public class CinemaListFragmentPresenter extends AppDelegate {
    private Context context;
    private XRecyclerView mCinemaList;
    private RecommendCinemaListAdapter cinemaListAdapter;
    private int page = 1;
    private String longitude = "116.30551391385724";
    private String latitude = "40.04571807462411";
    private String userId = "";
    private String sessionId = "";
    private boolean flag;
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

    public void initView(XRecyclerView mCinemaList, boolean flag) {
        this.mCinemaList = mCinemaList;
        this.flag = flag;
    }

    @Override
    public void initData() {
        super.initData();
        // 请求接口数据
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("count", 10 + "");
        map.put("longitude", longitude);
        map.put("latitude", latitude);

        getBean(0, HttpUrl.RECOMMEND_CINEMA, map, RecommendCinemaBean.class);
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
        switch (type) {
            case 0:
                RecommendCinemaBean cinemaBean = (RecommendCinemaBean) bean;
                //Log.i("bean",cinemaBean.getMessage());
                List<RecommendCinemaBean.ResultBean.FollowCinemasBean> followCinemas = cinemaBean.getResult().getFollowCinemas();
                List<RecommendCinemaBean.ResultBean.NearbyCinemaListBean> nearbyCinemaList = cinemaBean.getResult().getNearbyCinemaList();
                // 合并集合
                initList(followCinemas, nearbyCinemaList);
                cinemaListAdapter.setList0(list);

                break;
        }
    }

    private void initList(List<RecommendCinemaBean.ResultBean.FollowCinemasBean> followCinemas, List<RecommendCinemaBean.ResultBean.NearbyCinemaListBean> nearbyCinemaList) {
        // 合并集合方法
        if (flag){
            for (int i = 0; i < followCinemas.size(); i++) {
                RecommendCinemaBean.ResultBean.FollowCinemasBean bean = followCinemas.get(i);
                CinemaBean bean1 = new CinemaBean();
                bean1.setAddress(bean.getAddress());
                bean1.setCommentTotal(bean.getCommentTotal());
                bean1.setDistance(bean.getDistance());
                bean1.setFollowCinema(bean.isFollowCinema());
                bean1.setId(bean.getId());
                bean1.setLogo(bean.getLogo());
                bean1.setName(bean.getName());
                list.add(bean1);
            }
        }
        for (int i = 0; i < nearbyCinemaList.size(); i++) {
            RecommendCinemaBean.ResultBean.NearbyCinemaListBean bean = nearbyCinemaList.get(i);
            CinemaBean bean1 = new CinemaBean();
            bean1.setAddress(bean.getAddress());
            bean1.setCommentTotal(bean.getCommentTotal());
            bean1.setDistance(bean.getDistance());
            bean1.setFollowCinema(bean.isFollowCinema());
            bean1.setId(bean.getId());
            bean1.setLogo(bean.getLogo());
            bean1.setName(bean.getName());
           /* if (bean1.getDistance()>7000){
                continue;
            }*/
            list.add(bean1);
        }
    }
}

package com.bw.movie.presenter;

import android.content.Context;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaInfoActivity;
import com.bw.movie.mvp.model.CinemaInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;

import java.util.HashMap;
import java.util.Map;

public class AlertCinemaDetailFragmentPresenter extends AppDelegate{
    private Context context;
    private TextView location_txt,phone_txt,subway_txt,bus_txt,minWay_txt;
    private int cinemaId;
    @Override
    public int getLayout() {
        return R.layout.fragment_alert_cinema_detail;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(TextView location_txt, TextView phone_txt, TextView subway_txt, TextView bus_txt, TextView minWay_txt, int cinemaId) {
        this.location_txt = location_txt;
        this.phone_txt = phone_txt;
        this.subway_txt = subway_txt;
        this.bus_txt = bus_txt;
        this.minWay_txt = minWay_txt;
        this.cinemaId = cinemaId;
    }

    @Override
    public void initData() {
        super.initData();
        // 请求电影详情接口 展示电影详情信息
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
                location_txt.setText(infoBean.getResult().getAddress());
                phone_txt.setText(infoBean.getResult().getPhone());

                break;
        }
    }
}

package com.bw.movie.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.AttentionCinema;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.model.RecommendCinemaBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：owner on 2018/11/29 17:21
 */
public class AttentionCinemaFragmentPresenter extends AppDelegate {
    private XRecyclerView mXRecyclerView;
    private Context context;
    private List<AttentionCinema.ResultBean> result;

    @Override
    public int getLayout() {
        return R.layout.fragment_attention_cinema;
    }


    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(XRecyclerView mXRecyclerView) {
        this.mXRecyclerView = mXRecyclerView;
    }

    @Override
    public void initData() {
        super.initData();
        Map<String, String> map = new HashMap<>();
        RootMessage rootData = ShareUtil.getRootMessage(context);
        RootMessage.ResultBean userInfo = rootData.getResult();
        map.put("page", "1");
        map.put("count", "10");
        getBean(1, HttpUrl.STRING_ATTENTION_CINEMA, map, AttentionCinema.class, true);


    }


    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type) {
            default:
                break;
            case 1:
                AttentionCinema bean1 = (AttentionCinema) bean;
                 result = bean1.getResult();
                Log.i("AttentionCinemaFragment", bean1.getMessage()+"");
                break;
        }
    }

}

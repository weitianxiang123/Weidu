package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.AttentionCinemaAdpter;
import com.bw.movie.model.AttentionCinema;
import com.bw.movie.model.AttentionMovieBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.model.RecommendCinemaBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：owner on 2018/11/29 17:21
 */
public class AttentionCinemaFragmentPresenter extends AppDelegate {
    private Context context;
    private List<AttentionMovieBean.ResultBean> result;
    private XRecyclerView mXRecyclerView;
    private AttentionCinemaAdpter attentionCinemaAdpter;
    private List<AttentionMovieBean.ResultBean> nearbyCinemaListAll = new ArrayList<>();
    private List<AttentionMovieBean.ResultBean> nearbyCinemaList = new ArrayList<>();

    private int page = 1;

    @Override
    public int getLayout() {
        return R.layout.fragment_attention_cinema;
    }


    @Override
    public void initContext(Context context) {
        this.context = context;
    }


    public void initView(XRecyclerView mXRecyclerView) {
        this.mXRecyclerView = mXRecyclerView;
    }

    @Override
    public void initData() {
        super.initData();
        doHttp(page);

        mXRecyclerView.setLoadingMoreEnabled(true);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                attentionCinemaAdpter.setList(nearbyCinemaList);
                mXRecyclerView.loadMoreComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                doHttp(page);
            }
        });
    }

    private void doHttp(int page) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        RootMessage rootMessage = ShareUtil.getRootMessage(context);
        RootMessage.ResultBean result = rootMessage.getResult();
        headerMap.put("userId", result.getUserId() + "");
        headerMap.put("sessionId", result.getSessionId());
        map.put("page", page + "");
        map.put("count", "10");
        new HttpHelper(context).mineGet(HttpUrl.STRING_ATTENTION_MOVIES, map, headerMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                Log.i("AttentionCinemaFragment", data);
                AttentionMovieBean attentionCinema = new Gson().fromJson(data, AttentionMovieBean.class);
                nearbyCinemaList = attentionCinema.getResult();

                attentionCinemaAdpter = new AttentionCinemaAdpter(context);
                nearbyCinemaListAll.addAll(nearbyCinemaList);
                attentionCinemaAdpter.setList(nearbyCinemaList);
                //设置管理器
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                mXRecyclerView.setLayoutManager(manager);
                mXRecyclerView.setAdapter(attentionCinemaAdpter);

                mXRecyclerView.loadMoreComplete();
            }

            @Override
            public void fail(String error) {

            }
        });
    }


}

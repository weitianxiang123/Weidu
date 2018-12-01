package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.adapter.AttentionMovieAdpter;
import com.bw.movie.model.AttentionCinema;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
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
public class AttentionMovieFragmentPresenter extends AppDelegate {
    private Context context;
    private XRecyclerView mXRecyclerView;
    private AttentionMovieAdpter attentionMovieAdpter;
    private List<AttentionCinema.ResultBean> nearbyCinemaList;

    @Override
    public int getLayout() {
        return R.layout.fragment_attention_movie;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        doHttp();
        attentionMovieAdpter = new AttentionMovieAdpter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setAdapter(attentionMovieAdpter);
        // mXRecyclerView.setLoadingMoreEnabled(true);
        mXRecyclerView.setPullRefreshEnabled(true);
    }

    private void doHttp() {
        Map<String, String> map = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        RootMessage rootMessage = ShareUtil.getRootMessage(context);
        RootMessage.ResultBean result = rootMessage.getResult();
        headerMap.put("userId", result.getUserId() + "");
        headerMap.put("sessionId", result.getSessionId());
        map.put("page", "1");
        map.put("count", "10");
        new HttpHelper(context).mineGet(HttpUrl.STRING_ATTENTION_CINEMA, map, headerMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                Log.i("AttentionMovieFragmentP", data);
                AttentionCinema attentionCinema = new Gson().fromJson(data, AttentionCinema.class);
                nearbyCinemaList = attentionCinema.getResult();
                //判断数据下标 如果为空就不加载
                if (nearbyCinemaList.size() == 0) {
                    return;
                }
                attentionMovieAdpter.setList(nearbyCinemaList);

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void initView(XRecyclerView mXRecyclerView) {
        this.mXRecyclerView = mXRecyclerView;
    }
}

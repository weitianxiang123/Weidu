package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.adapter.RccordAdpter;
import com.bw.movie.model.RccordBean;
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
 * 作者：owner on 2018/12/2 19:00
 */
public class RccordActivityPresenter extends AppDelegate {
    private XRecyclerView mXRecyclerView;
    private Context context;
    private RccordAdpter rccordAdpter;
    private List<RccordBean.ResultBean> result1;

    @Override
    public int getLayout() {
        return R.layout.activity_rccord;
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
        rccordAdpter = new RccordAdpter(context);
        rccordAdpter.setList(result1);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setAdapter(rccordAdpter);
        doHttp();
    }

    /**
     * 购片信息页面的网络请求
     */
    private void doHttp() {
        Map<String, String> map = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        RootMessage rootMessage = ShareUtil.getRootMessage(context);
        RootMessage.ResultBean result = rootMessage.getResult();
        headerMap.put("userId", result.getUserId() + "");
        headerMap.put("sessionId", result.getSessionId());
        map.put("page", "1");
        map.put("count", "5");
        new HttpHelper(context).mineGet(HttpUrl.STRING_RECORD_SELECT, map, headerMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                RccordBean rccordBean = new Gson().fromJson(data, RccordBean.class);
                result1 = rccordBean.getResult();
                if (result1.size() == 0) {
                    return;
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}

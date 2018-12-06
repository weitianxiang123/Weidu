package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.ReMindAdapter;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.MessageContextBean;
import com.bw.movie.model.ReMindBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：owner on 2018/11/29 10:31
 */
public class RemindActivityPresenter extends AppDelegate {
    private Context context;
    private TextView mRemindNum;
    private XRecyclerView mRemindXrecyclerview;
    private ReMindAdapter reMindAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_remind;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }


    public void initView(TextView mRemindNum, XRecyclerView mRemindXrecyclerview) {
        this.mRemindNum = mRemindNum;
        this.mRemindXrecyclerview = mRemindXrecyclerview;
    }

    @Override
    public void initData() {
        super.initData();

        reMindAdapter = new ReMindAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRemindXrecyclerview.setLayoutManager(manager);
        mRemindXrecyclerview.setAdapter(reMindAdapter);
        mRemindXrecyclerview.setPullRefreshEnabled(false);
        doHttp();//获取消息
        messageContext();//获取未读数量

    }

    /**
     * 系统消息的网络请求
     */
    private void doHttp() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "5");
        new HttpHelper(context).mineGet(HttpUrl.SYSM_SGLIST, map, null).result(new HttpListener() {
            @Override
            public void success(String data) {
                ReMindBean reMindBean = new Gson().fromJson(data, ReMindBean.class);
                List<ReMindBean.ResultBean> result = reMindBean.getResult();
                reMindAdapter.setList(result);
                /**
                 * 点级条目时触发此事件来重新刷新未读数量
                 */
                reMindAdapter.results(new ReMindAdapter.RefreshCallBack() {
                    @Override
                    public void callback() {
                        messageContext();
                    }
                });
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    /**
     * 未读消息数量的网络请求
     */
    private void messageContext() {

        new HttpHelper(context).mineGet(HttpUrl.MESSAGE_CONTENT, null, null).result(new HttpListener() {
            @Override
            public void success(String data) {
                MessageContextBean loginBean = new Gson().fromJson(data, MessageContextBean.class);
                if (loginBean.getCount() == 0) {
                    mRemindNum.setText("系统消息：(目前没有未读消息)");
                } else {
                    mRemindNum.setText("系统消息 (" + loginBean.getCount() + ")条未读");

                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }
}

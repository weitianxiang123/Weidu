package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.FeedBackSuccessActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：owner on 2018/11/28 18:47
 */
public class FeedbackActivityPresenter extends AppDelegate {
    private Context context;
    private EditText mFeedBackEd;


    @Override
    public int getLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void initView(EditText mFeedBackEd) {
        this.mFeedBackEd = mFeedBackEd;
    }

    @Override
    public void initData() {
        super.initData();

    }

    public void feedBack() {
        String s = mFeedBackEd.getText().toString();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(context, "亲！反馈内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        RootMessage.ResultBean result = ShareUtil.getRootMessage(context).getResult();
        headerMap.put("userId", result.getUserId() + "");
        headerMap.put("sessionId", result.getSessionId());
        map.put("content", s);
        new HttpHelper(context).minePost(HttpUrl.APP_FEED_BACK, map, headerMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                if ("0000".equals(loginBean.getStatus())) {
                    context.startActivity(new Intent(context, FeedBackSuccessActivity.class));
                    ((Activity) context).finish();
                } else {
                    return;
                }
            }

            @Override
            public void fail(String error) {
                Log.i("FeedbackActivityPresent", "失败");
            }
        });
    }
}

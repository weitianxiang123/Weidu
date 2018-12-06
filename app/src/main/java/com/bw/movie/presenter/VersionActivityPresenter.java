package com.bw.movie.presenter;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.AttentionCinema;
import com.bw.movie.model.RootMessage;
import com.bw.movie.model.VersionCodeBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：owner on 2018/11/29 09:41
 */
public class VersionActivityPresenter extends AppDelegate {
    private Context context;
    private TextView versionCode;

    @Override
    public int getLayout() {
        return R.layout.activity_version;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void initView(TextView versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public void initData() {
        super.initData();
        Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show();
        Map<String, String> map = new HashMap<>();
        RootMessage.ResultBean result = ShareUtil.getRootMessage(context).getResult();
        map.put("userId", result.getUserId()+ "");
        map.put("sessionId", result.getSessionId());
        map.put("ak", "0110010010000");
        getString(0, HttpUrl.APP_VERSION_CODE, map,false);

    }

    /**
     * 检查更新的网络请求
     * @param type
     * @param data
     */
    @Override
    public void successString(int type, String data) {
        super.successString(type, data);
        switch (type) {
            default:
                break;
            case 0:
                VersionCodeBean versionCodeBean = new Gson().fromJson(data, VersionCodeBean.class);

                if (versionCodeBean.getFlag() == 1) {
                    versionCode.setText("请前往此地下载：" +versionCodeBean.getDownloadUrl());
                } else {
                    versionCode.setText("已经是最新版本");

                }

                break;
        }
    }
}

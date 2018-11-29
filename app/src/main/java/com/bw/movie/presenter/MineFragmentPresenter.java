package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.activity.FeedbackActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.activity.RccordActivity;
import com.bw.movie.activity.RemindActivity;
import com.bw.movie.activity.VersionActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.ShareUtil;

/**
 * 作者：owner on 2018/11/28 11:07
 */
public class MineFragmentPresenter extends AppDelegate {
    private Context context;

    @Override
    public int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void tomessiage() {
        //判断当前登录状态
        if (ShareUtil.isLogin(context)) {
            //跳转用户信息也面
            context.startActivity(new Intent(context, MessiageActivity.class));
        } else {
            //跳转登录用户页面
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void toattention() {
        //if (login) {
            context.startActivity(new Intent(context, AttentionActivity.class));
       // } else {
         //   toast();
       // }
    }


    public void torccord() {
       // if (login) {
            context.startActivity(new Intent(context, RccordActivity.class));
       // } else {
        //    toast();
       // }
    }

    public void tofeedback() {
        if (ShareUtil.isLogin(context)) {
            context.startActivity(new Intent(context, FeedbackActivity.class));
        } else {
            toast();
        }
    }

    public void toversion() {
        context.startActivity(new Intent(context, VersionActivity.class));
    }

    public void toremind() {
        context.startActivity(new Intent(context, RemindActivity.class));
    }

    private void toast() {
        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
    }
}

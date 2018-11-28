package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.activity.FeedbackActivity;
import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.activity.RccordActivity;
import com.bw.movie.activity.RemindActivity;
import com.bw.movie.activity.VersionActivity;
import com.bw.movie.mvp.view.AppDelegate;

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
        context.startActivity(new Intent(context, MessiageActivity.class));
    }


    public void toattention() {
        context.startActivity(new Intent(context, AttentionActivity.class));
    }

    public void torccord() {
        context.startActivity(new Intent(context, RccordActivity.class));
    }

    public void tofeedback() {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    public void toversion() {
        context.startActivity(new Intent(context, VersionActivity.class));
    }

    public void toremind() {
        context.startActivity(new Intent(context, RemindActivity.class));
    }

}

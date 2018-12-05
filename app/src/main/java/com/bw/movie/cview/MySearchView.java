package com.bw.movie.cview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

public class MySearchView extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private RelativeLayout content,faker;
    private EditText editTxt;
    public MySearchView(Context context) {
        super(context);
        init(context);
    }


    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.layout_search, null);
        faker = view.findViewById(R.id.faker);
        content = view.findViewById(R.id.contentLayout);
        ImageView img = view.findViewById(R.id.search_img);
        editTxt = view.findViewById(R.id.search_edit);
        TextView toSearch = view.findViewById(R.id.toSearch);
        faker.setOnClickListener(this);
        img.setOnClickListener(this);
        toSearch.setOnClickListener(this);
        content.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        addView(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.faker:
                //点击展示真正的搜索框 隐藏表体
                showTrueView();
                //content.setVisibility(View.VISIBLE);
                break;
            case R.id.search_img:
                //showTrueView();
                // 点击隐藏真正的搜索框 显示表体
                hideTrueView();
                break;
            case R.id.toSearch:
                // 点击跳转到搜索展示页
                toSearchCinema();
                break;

        }
    }

    // 点击跳转到搜索展示页
    private void toSearchCinema() {
        String txt = editTxt.getText().toString().trim();
        /*if (TextUtils.isEmpty(txt)){
            Toast.makeText(context,"输入为空",Toast.LENGTH_SHORT).show();
            return;
        }*/
        // 传递搜索内容
        if (listener!=null){
            listener.Search(txt);
        }

    }

    // 点击隐藏真正的搜索框 显示表体
    private void hideTrueView() {
        // 获取宽度
        int width = context.getResources().getDisplayMetrics().widthPixels;
        float fakerX = faker.getX();
        // 设置动画  让它从下面滑出来 1s动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(content, "translationX",(float)0,fakerX);
        animator.setDuration(500);
        animator.start();
        // 彻底移除
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                content.setVisibility(View.GONE);
            }
        }, 500);
    }

    // 点击展示真正的搜索框 隐藏表体
    private void showTrueView() {
        // 获取宽度
        int width = context.getResources().getDisplayMetrics().widthPixels;
        float fakerX = faker.getX();
        // 设置动画  让它从下面滑出来 1s动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(content, "translationX",fakerX,(float)0);
        animator.setDuration(500);
        animator.start();
        content.setVisibility(View.VISIBLE);

    }
    // 传递接口
    public interface OnSearchListener{
        void Search(String txt);
    }
    private OnSearchListener listener;
    public void setOnSearchListener(OnSearchListener listener){
        this.listener = listener;
    }
}

package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaCommentListAdapter;
import com.bw.movie.mvp.model.CinemaCommentBean;
import com.bw.movie.mvp.model.CinemaFollowMessageBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertMovieRecommendFragmentPresenter extends AppDelegate implements CinemaCommentListAdapter.OnGreatListener {
    private Context context;
    private XRecyclerView mRecyclerList;
    private int cinemaId;
    private int page = 1;
    private List<CinemaCommentBean.ResultBean> list = new ArrayList<>();
    private CinemaCommentListAdapter commentListAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_alert_cinema_recommend;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(XRecyclerView mRecyclerList, int cinemaId) {
        this.mRecyclerList = mRecyclerList;
        this.cinemaId = cinemaId;
    }

    @Override
    public void initData() {
        super.initData();
        // 请求影院评论接口  适配内容
        commentListAdapter = new CinemaCommentListAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerList.setLayoutManager(manager);
        mRecyclerList.setAdapter(commentListAdapter);

        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        map.put("page", page + "");
        map.put("count", 10 + "");
        getBean(0, HttpUrl.CINEMA_COMMENT_ALL, map, CinemaCommentBean.class, true);// 此处要改为true

        // 监听点赞
        commentListAdapter.setOnGreatListener(this);
        // 评论  添加头布局
        View view = View.inflate(context, R.layout.cinema_comment_layout, null);
        TextView showComment = view.findViewById(R.id.showComment);
        TextView submitComment = view.findViewById(R.id.submit_comment);
        EditText editTxt = view.findViewById(R.id.edit_txt);
        RelativeLayout faker = view.findViewById(R.id.faker);
        RelativeLayout commentLayout = view.findViewById(R.id.commentLayout);
        // 点击我要评论 隐藏faker 展示评论布局  点击发布评论 请求接口 返回结果 隐藏  再显示faker
        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faker.setVisibility(View.GONE);
                commentLayout.setVisibility(View.VISIBLE);
            }
        });
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = editTxt.getText().toString().trim();
                if (TextUtils.isEmpty(txt)){
                    Toast.makeText(context,"不能为空",Toast.LENGTH_SHORT).show();
                    commentLayout.setVisibility(View.GONE);
                    faker.setVisibility(View.VISIBLE);
                    return;
                }
                // 请求评论接口
                comment(faker,commentLayout,txt);
            }
        });
        mRecyclerList.addHeaderView(view);

    }

    private void comment(RelativeLayout faker, RelativeLayout commentLayout,String txt) {
        // 评论
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        map.put("commentContent",txt);
        new HttpHelper(context).lrHead(HttpUrl.CINEMA_COMMENT,map,null).result(new HttpListener() {
            @Override
            public void success(String data) {
                CinemaFollowMessageBean messageBean = new Gson().fromJson(data, CinemaFollowMessageBean.class);
                Toast.makeText(context, messageBean.getMessage(), Toast.LENGTH_SHORT).show();
                if ("评论成功".equals(messageBean.getMessage())) {
                    commentLayout.setVisibility(View.GONE);
                    faker.setVisibility(View.VISIBLE);
                    refresh();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type) {
            case 0:
                CinemaCommentBean commentBean = (CinemaCommentBean) bean;
                list = commentBean.getResult();
                commentListAdapter.setList(list);
                break;
            /*case 1:
                CinemaFollowMessageBean messageBean = (CinemaFollowMessageBean) bean;
                Toast.makeText(context,messageBean.getMessage(),Toast.LENGTH_SHORT).show();
                if ("点赞成功".equals(messageBean.getMessage())){
                    refresh();
                }
                break;*/
        }
    }

    private void refresh() {
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        map.put("page", page + "");
        map.put("count", 10 + "");
        getBean(0, HttpUrl.CINEMA_COMMENT_ALL, map, CinemaCommentBean.class, true);// 此处要改为true
    }

    @Override
    public void onGreat(int position) {
        // 点赞
        int commentId = list.get(position).getCommentId();
        Map<String, String> map = new HashMap<>();
        map.put("commentId", commentId + "");
        new HttpHelper(context).lrHead(HttpUrl.CINEMA_COMMENT_GREAT, map, null).result(new HttpListener() {
            @Override
            public void success(String data) {
                CinemaFollowMessageBean messageBean = new Gson().fromJson(data, CinemaFollowMessageBean.class);
                Toast.makeText(context, messageBean.getMessage(), Toast.LENGTH_SHORT).show();
                if ("点赞成功".equals(messageBean.getMessage())) {
                    refresh();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
        //postBean(1,HttpUrl.CINEMA_COMMENT_GREAT,map, CinemaFollowMessageBean.class,true);
    }
}

package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CinemaCommentBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

// 影院评论适配器
public class CinemaCommentListAdapter extends XRecyclerView.Adapter<CinemaCommentListAdapter.MyHolder>{
    private Context context;
    private List<CinemaCommentBean.ResultBean> list = new ArrayList<>();

    public CinemaCommentListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CinemaCommentBean.ResultBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.cinema_comment_list_item,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        CinemaCommentBean.ResultBean bean = list.get(i);
        myHolder.commentHeadPic.setImageURI(bean.getCommentHeadPic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends XRecyclerView.ViewHolder{
        SimpleDraweeView commentHeadPic;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            commentHeadPic = itemView.findViewById(R.id.commentHeadPic);
        }
    }
}

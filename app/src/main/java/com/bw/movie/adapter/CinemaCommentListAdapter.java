package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CinemaCommentBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

// 影院评论适配器
public class CinemaCommentListAdapter extends XRecyclerView.Adapter<CinemaCommentListAdapter.MyHolder> {
    private Context context;
    private List<CinemaCommentBean.ResultBean> list = new ArrayList<>();

    public CinemaCommentListAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CinemaCommentBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_comment_list_item, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        CinemaCommentBean.ResultBean bean = list.get(i);
        myHolder.commentHeadPic.setImageURI(bean.getCommentHeadPic());
        myHolder.commentUserName.setText(bean.getCommentUserName());
        myHolder.commentContent.setText(bean.getCommentContent());
        myHolder.commentTime.setText(bean.getCommentTime() + "");
        myHolder.greatNum.setText(bean.getGreatNum() + "");
        if (bean.getIsGreat() == 1) {
            // 已经点赞
            myHolder.isGreat.setImageResource(R.drawable.com_icon_praise_selected);
        } else {
            // 没有点赞
            myHolder.isGreat.setImageResource(R.drawable.com_icon_praise_default);
        }
        myHolder.isGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击  点赞  请求点赞接口
                if (listener != null) {
                    listener.onGreat(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends XRecyclerView.ViewHolder {
        SimpleDraweeView commentHeadPic;
        TextView commentUserName, commentContent, commentTime, greatNum;
        ImageView isGreat;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            commentHeadPic = itemView.findViewById(R.id.commentHeadPic);
            commentUserName = itemView.findViewById(R.id.commentUserName);
            commentContent = itemView.findViewById(R.id.commentContent);
            commentTime = itemView.findViewById(R.id.commentTime);
            greatNum = itemView.findViewById(R.id.greatNum);
            isGreat = itemView.findViewById(R.id.isGreat);
        }
    }

    // 传递接口
    private OnGreatListener listener;

    public void setOnGreatListener(OnGreatListener listener) {
        this.listener = listener;
    }

    public interface OnGreatListener {
        void onGreat(int position);
    }
}

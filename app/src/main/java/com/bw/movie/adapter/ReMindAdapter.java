package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.MovieItem;
import com.bw.movie.model.ReMindBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.DateFormatForYou;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张秋阳
 * mine碎片的自条目适配器
 */
public class ReMindAdapter extends RecyclerView.Adapter<ReMindAdapter.MyViewHolder> {
    private Context context;
    private List<ReMindBean.ResultBean> list = new ArrayList<>();

    public ReMindAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReMindBean.ResultBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.remind_item_show, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ReMindBean.ResultBean resultBean = list.get(i);
        myViewHolder.content.setText(resultBean.getContent());

        try {
            myViewHolder.pushTime.setText(new DateFormatForYou().longToString(resultBean.getPushTime(), "hh-mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (resultBean.getStatus() == 0) {
            myViewHolder.status.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.status.setVisibility(View.GONE);
        }
        myViewHolder.title.setText(resultBean.getTitle());
        myViewHolder.cancleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, String> map = new HashMap<>();
                map.put("id", list.get(i).getId() + "");
                new HttpHelper(context).mineGet(HttpUrl.MSG_STATUS, map, null).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        myViewHolder.status.setVisibility(View.GONE);
                        refreshCallBack.callback();
                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String error) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ReMindBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView pushTime;
        TextView status;
        TextView title;
        LinearLayout cancleLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.remind_content);
            pushTime = itemView.findViewById(R.id.remind_pushTime);
            status = itemView.findViewById(R.id.remind_status);
            title = itemView.findViewById(R.id.remind_title);
            cancleLayout = itemView.findViewById(R.id.cancle_layout);
        }
    }
    private RefreshCallBack  refreshCallBack;

    public void results(RefreshCallBack refreshCallBack) {
        this.refreshCallBack = refreshCallBack;
    }

    public interface RefreshCallBack{
        void callback();
    }
}

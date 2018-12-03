package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MovieDetailsActivity;
import com.bw.movie.model.AttentionMovieBean;
import com.bw.movie.model.RccordBean;
import com.bw.movie.utils.DateFormatForYou;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：owner on 2018/11/30 11:22
 */
public class RccordAdpter extends RecyclerView.Adapter<RccordAdpter.Mviewhodler> {
    private Context context;
    private List<RccordBean.ResultBean> list = new ArrayList<>();

    public RccordAdpter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RccordAdpter.Mviewhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.rccord_item_layout, null);
        Mviewhodler mviewhodler = new Mviewhodler(inflate);
        return mviewhodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RccordAdpter.Mviewhodler mviewhodler, final int i) {
        /*
        因为暂时没有完善支付功能，适配器还没有完成
         */


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<RccordBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class Mviewhodler extends RecyclerView.ViewHolder {
        TextView nameTextView, numberTextView, cinemaTextView,
                screensTextView, dataTextView, numTextView, amountTextView;
        Button buyButton;
        LinearLayout btLayout;

        public Mviewhodler(@NonNull View itemView) {
            super(itemView);
            btLayout = itemView.findViewById(R.id.rccord_layout);
            //条目布局用来点击
            btLayout = itemView.findViewById(R.id.creat_time);
            //开始时间
            nameTextView = itemView.findViewById(R.id.rccord_name);
            //影片名
            buyButton = itemView.findViewById(R.id.rccord_buy);
            //如果未支付点击支付
            numberTextView = itemView.findViewById(R.id.rccord_number);
            //订单号
            cinemaTextView = itemView.findViewById(R.id.rccord_cinema);
            //影院
            screensTextView = itemView.findViewById(R.id.rccord_screens);
            //影厅
            dataTextView = itemView.findViewById(R.id.rccord_data);
            //播放时间
            numTextView = itemView.findViewById(R.id.rccord_num);
            //购买电影票数量
            amountTextView = itemView.findViewById(R.id.rccord_amount);
            //总金额
        }

    }
}

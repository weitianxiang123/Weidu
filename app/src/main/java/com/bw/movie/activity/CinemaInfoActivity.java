package com.bw.movie.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.CinemaInfoActivityPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import recycler.coverflow.RecyclerCoverFlow;
/*
* 影院详情页面
* */
public class CinemaInfoActivity extends BaseActivity<CinemaInfoActivityPresenter>{
    @BindView(R.id.cinema_name)
    TextView cinemaName;
    @BindView(R.id.cinema_logo)
    SimpleDraweeView cinemaLogo;
    @BindView(R.id.cinema_location)
    TextView cinemaLocation;
    @BindView(R.id.showInfo)
    ImageView showInfo;
    @BindView(R.id.recycleRotate)
    RecyclerCoverFlow mRecyclerCoverFlow;
    @BindView(R.id.movieOptionList)
    XRecyclerView mMovieOptionList;
    // 弹出框
    @BindView(R.id.info_alert)
    RelativeLayout info_alert;
    @BindView(R.id.txt_to_detail)
    TextView toDetail;
    @BindView(R.id.txt_to_comment)
    TextView toCommend;
    @BindView(R.id.close_info)
    ImageView closeImg;
    @BindView(R.id.info_pager)
    ViewPager info_pager;

    @Override
    public Class<CinemaInfoActivityPresenter> getClassDelegate() {
        return CinemaInfoActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        delegate.initView(cinemaName,cinemaLogo,cinemaLocation,showInfo,mRecyclerCoverFlow,mMovieOptionList,cinemaId,info_alert,toDetail,toCommend,closeImg,info_pager);
    }

    @OnClick({R.id.close_info,R.id.txt_to_detail,R.id.txt_to_comment,R.id.cinema_logo})
    public void click(View view){
        switch (view.getId()){
            case R.id.close_info:
                // 关闭弹出页
                delegate.closeAlert();
                break;
            case R.id.txt_to_detail:
                // 前往详情
                delegate.toDetails();
                break;
            case R.id.txt_to_comment:
                // 前往评论
                delegate.toComment();
                break;
            case R.id.cinema_logo:
                // 展开弹出框
                delegate.showAlert();
                break;

        }
    }
}

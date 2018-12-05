package com.bw.movie.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.AlertMovieRecommendFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class AlertCinemaRecommendFragment extends BaseFragment<AlertMovieRecommendFragmentPresenter>{
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerList;
    @Override
    public Class<AlertMovieRecommendFragmentPresenter> getClassDelegate() {
        return AlertMovieRecommendFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        int cinemaId = getArguments().getInt("cinemaId");
        delegate.initView(mRecyclerList,cinemaId);
    }
}

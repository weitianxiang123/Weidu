package com.bw.movie.fragment;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.AttentionCinemaFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;


public class AttentionCinemaFragment extends BaseFragment<AttentionCinemaFragmentPresenter> {

    @BindView(R.id.attention_cinema_xrecycler)
    XRecyclerView mXRecyclerView;
    @Override
    public Class<AttentionCinemaFragmentPresenter> getClassDelegate() {

        return AttentionCinemaFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mXRecyclerView);
    }
}

package com.bw.movie.fragment;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.AttentionMovieFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;


public class AttentionMovieFragment extends BaseFragment<AttentionMovieFragmentPresenter> {
@BindView(R.id.attention_movie_xrecycler)XRecyclerView mXRecyclerView;

    @Override
    public Class<AttentionMovieFragmentPresenter> getClassDelegate() {
        return AttentionMovieFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mXRecyclerView);
    }
}

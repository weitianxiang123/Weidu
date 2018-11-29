package com.bw.movie.fragment;

import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.AttentionMovieFragmentPresenter;


public class AttentionMovieFragment extends BaseFragment<AttentionMovieFragmentPresenter> {


    @Override
    public Class<AttentionMovieFragmentPresenter> getClassDelegate() {
        return AttentionMovieFragmentPresenter.class;
    }
}

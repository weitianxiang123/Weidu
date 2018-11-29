package com.bw.movie.fragment;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.CinemaListFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class CinemaListFragment extends BaseFragment<CinemaListFragmentPresenter>{
    @BindView(R.id.cinema_list)
    XRecyclerView mCinemaList;
    @Override
    public Class<CinemaListFragmentPresenter> getClassDelegate() {
        return CinemaListFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mCinemaList);
    }
}

package com.bw.movie.activity;

import android.support.v7.widget.SearchView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.CinemaSearchActivityPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class CinemaSearchActivity extends BaseActivity<CinemaSearchActivityPresenter>{
    @BindView(R.id.cinema_search)
    SearchView searchView;
    @BindView(R.id.searchCinemaList)
    XRecyclerView mSearchCinemaListCinemaList;
    @Override
    public Class<CinemaSearchActivityPresenter> getClassDelegate() {
        return CinemaSearchActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        String searchString = getIntent().getStringExtra("searchString");
        delegate.initView(searchView,mSearchCinemaListCinemaList,searchString);
    }
}

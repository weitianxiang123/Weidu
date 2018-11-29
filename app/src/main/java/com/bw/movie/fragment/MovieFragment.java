package com.bw.movie.fragment;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.MovieFragmentPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

/**
 * @author 李地坤
 * @date  11/28
 *
 * 首页中的电影页面碎片
 */
public class MovieFragment extends BaseFragment<MovieFragmentPresenter> {

	@BindView(R.id.xRefresh)
	XRecyclerView xRecyclerView;
	@Override
	public Class<MovieFragmentPresenter> getClassDelegate() {

		return MovieFragmentPresenter.class;
	}

	@Override
	public void initView() {
		super.initView();
	delegate.initView(xRecyclerView,getChildFragmentManager());

	}
}

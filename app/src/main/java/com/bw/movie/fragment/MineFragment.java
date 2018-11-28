package com.bw.movie.fragment;

import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.MineFragmentPresenter;

/**
 * 作者：owner on 2018/11/28 09:41
 */
public class MineFragment extends BaseFragment<MineFragmentPresenter>{

    @Override
    public Class<MineFragmentPresenter> getClassDelegate() {
        return MineFragmentPresenter.class;
    }
}

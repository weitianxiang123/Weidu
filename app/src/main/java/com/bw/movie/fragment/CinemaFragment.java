package com.bw.movie.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.cview.MySearchView;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.CinemaFragmentPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CinemaFragment extends BaseFragment<CinemaFragmentPresenter>{
    @BindView(R.id.toLocation)
    ImageView toLocation;
    @BindView(R.id.city)
    TextView city;
    /*@BindView(R.id.search)
    RelativeLayout search;*/
    @BindView(R.id.search)
    MySearchView search;
    @BindView(R.id.recommendCinema)
    TextView recommendCinema;
    @BindView(R.id.nearbyCinema)
    TextView nearbyCinema;
    @BindView(R.id.cinema_pager)
    ViewPager cinemaPager;

    @Override
    public Class<CinemaFragmentPresenter> getClassDelegate() {
        return CinemaFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(toLocation,city,search,recommendCinema,nearbyCinema,cinemaPager);
    }

    @OnClick({R.id.recommendCinema,R.id.nearbyCinema})
    public void click(View view){
        switch (view.getId()){
            case R.id.recommendCinema:
                delegate.toRecommend();

                break;
            case R.id.nearbyCinema:
                delegate.toNearby();
                break;
        }
    }
}

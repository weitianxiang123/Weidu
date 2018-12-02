package com.bw.movie.fragment;

import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.AlertCinemaDetailFragmentPresenter;

import butterknife.BindView;

public class AlertCinemaDetailFragment extends BaseFragment<AlertCinemaDetailFragmentPresenter>{
    @BindView(R.id.cinema_location)
    TextView location_txt;
    @BindView(R.id.cinema_phone)
    TextView phone_txt;
    @BindView(R.id.subway)
    TextView subway_txt;
    @BindView(R.id.bus_way)
    TextView bus_txt;
    @BindView(R.id.mine_way)
    TextView minWay_txt;
    @Override
    public Class<AlertCinemaDetailFragmentPresenter> getClassDelegate() {
        return AlertCinemaDetailFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        int cinemaId = getArguments().getInt("cinemaId");
        delegate.initView(location_txt,phone_txt,subway_txt,bus_txt,minWay_txt,cinemaId);
    }
}

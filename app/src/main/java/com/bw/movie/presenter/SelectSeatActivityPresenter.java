package com.bw.movie.presenter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.cview.SeatTable;
import com.bw.movie.mvp.view.AppDelegate;

public class SelectSeatActivityPresenter extends AppDelegate {
	private Context context;
	private SeatTable seatTable;

	@Override
	public int getLayout() {
		return R.layout.activity_select_seat;
	}

	@Override
	public void initContext(Context context) {
		super.initContext(context);
	    this.context=context;

	}

	public void initView(SeatTable seatTable) {
		this.seatTable=seatTable;
	}
}

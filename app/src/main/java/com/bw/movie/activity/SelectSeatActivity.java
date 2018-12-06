package com.bw.movie.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.cview.SeatTable;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.SelectSeatActivityPresenter;
import com.bw.movie.utils.LocationUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectSeatActivity extends BaseActivity<SelectSeatActivityPresenter> {
@BindView(R.id.seatView)
SeatTable seatTable;
@BindView(R.id.textPrice)
TextView textPrice;
@BindView(R.id.imageConform)
ImageView imageConform;
@BindView(R.id.imageCancel)
ImageView imageCancel;

	@Override
	public Class<SelectSeatActivityPresenter> getClassDelegate() {
		return SelectSeatActivityPresenter.class;
	}

	@Override
	public void initView() {
		super.initView();

		Intent intent = getIntent();
		int scheduleId = intent.getIntExtra("scheduleId", 0);
		double price = intent.getDoubleExtra("price", 0);
		seatTable.setScreenName("哈哈影城");
		seatTable.setMaxSelected(5);
		seatTable.setSeatChecker(new SeatTable.SeatChecker() {

			@Override
			public boolean isValidSeat(int row, int column) {
				if((row==3||row==4)&&column==3) {
					return false;
				}

				return true;
			}

			@Override
			public boolean isSold(int row, int column) {
				//已售
				if(row==6&&column==6){
					return true;
				}
				return false;
			}

			@Override
			public void checked(int row, int column) {
             //选中

				delegate.add();

			}

			@Override
			public void unCheck(int row, int column) {
             //未选中
				delegate.subtraction();
			}

			@Override
			public String[] checkedSeatTxt(int row, int column) {
				//选中更改显示文字，一个长度为二的数组，长度为一就居中显示
				return null;
			}

		});

		seatTable.setData(10,15);
	    delegate.initView(seatTable,scheduleId,price,textPrice);

	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if (delegate.cancelPop())
		{
           return;
		}

	}

	@OnClick({R.id.imageConform,R.id.imageCancel}) void onClick(View view){

		switch (view.getId()){
			case  R.id.imageCancel:
				finish();
				delegate.cancel();
				break;
			case R.id.imageConform:

				delegate.conform();
				break;

		}


	}
}


/**
 *public SeatTable seatTableView;
 * @Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setContentView(R.layout.activity_select_seat);


seatTableView =  findViewById(R.id.seatView);
seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
seatTableView.setMaxSelected(3);//设置最多选中
seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

@Override
public boolean isValidSeat(int row, int column) {
if(column==2) {
return false;
}
return true;
}

@Override
public boolean isSold(int row, int column) {
if(row==6&&column==6){
return true;
}
return false;
}

@Override
public void checked(int row, int column) {
//选中


}

@Override
public void unCheck(int row, int column) {
//未选中

}

@Override
public String[] checkedSeatTxt(int row, int column) {
return null;
}

});
seatTableView.setData(10,15);
}


 *
 */

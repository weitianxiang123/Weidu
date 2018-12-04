package com.bw.movie.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.cview.SeatTable;
import com.bw.movie.utils.LocationUtils;

import java.io.IOException;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity {
	public SeatTable seatTableView;
	private Location location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_select_seat);

		Location location = LocationUtils.getInstance(this).showLocation();


		if(location!=null)
		{
			Toast.makeText(this, ""+location.getLongitude(), Toast.LENGTH_SHORT).show();
			Geocoder geocoder=new Geocoder(this);

			try {
				List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				Address address = fromLocation.get(0);

				Toast.makeText(this, ""+address.getFeatureName(), Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		seatTableView = (SeatTable) findViewById(R.id.seatView);
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

			}

			@Override
			public void unCheck(int row, int column) {
			}

			@Override
			public String[] checkedSeatTxt(int row, int column) {
				return null;
			}

		});
		seatTableView.setData(10,15);
	}





}

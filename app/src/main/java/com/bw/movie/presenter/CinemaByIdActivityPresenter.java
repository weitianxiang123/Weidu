package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CineamByIdAdapter;
import com.bw.movie.model.CinemaById;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class CinemaByIdActivityPresenter  extends AppDelegate{
	private Context context;
    int movieId;
    XRecyclerView xRecyclerView;
	private CineamByIdAdapter cineamByIdAdapter;

	@Override
	public int getLayout() {
		return R.layout.activity_cinema_by_id;
	}


	@Override
	public void initContext(Context context) {
		super.initContext(context);
	this.context=context;

	}

	@Override
	public void initData() {
		super.initData();

		 xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		cineamByIdAdapter = new CineamByIdAdapter(context);
		cineamByIdAdapter.setMovieId(movieId);
		xRecyclerView.setAdapter(cineamByIdAdapter);

		Map<String,String> map=new HashMap<>();
		map.put("movieId",movieId+"");
		getString(1, HttpUrl.STRING_CINEMA_BY_ID,map,true);


	}

	public void initView(int movieId, XRecyclerView xRecyclerView) {

		this.movieId=movieId;
		this.xRecyclerView=xRecyclerView;
	}

	@Override
	public void successString(int type, String data) {
		super.successString(type, data);
     switch (type){
     	case 1:
			CinemaById cinemaById = new Gson().fromJson(data, CinemaById.class);
            cineamByIdAdapter.setData(cinemaById);
            cineamByIdAdapter.notifyDataSetChanged();
			break;

	 }

	}
}

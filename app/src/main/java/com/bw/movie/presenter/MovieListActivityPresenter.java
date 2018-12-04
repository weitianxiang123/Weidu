package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieListAdapter;
import com.bw.movie.model.MovieItem;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpUrl;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieListActivityPresenter extends AppDelegate {
	private int page0=1;
	private int page1=1;
	private int page2=1;
	private int page3=1;
	private int count=5;
    private boolean isLoadMore;
    private boolean isRefrush;

	private Context context;
	private XRecyclerView xRecyclerView;
	private MovieListAdapter movieListAdapter;
	private MovieItem mMovieItem;
	private TextView textHot;
	private TextView textNow;
	private TextView textWill;
    private List<TextView> buttons;
	private int type;

	@Override
	public int getLayout() {
		return R.layout.activity_movie_list;
	}

	@Override
	public void initContext(Context context) {
		super.initContext(context);
	this.context=context;
	}

	@Override
	public void initData() {
		super.initData();

		Activity activity=(Activity)context;
		Intent intent = activity.getIntent();
		type = intent.getIntExtra("type", 0);
		buttons=new ArrayList<>();
		buttons.add(textHot);
		buttons.add(textNow);
		buttons.add(textWill);


		xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		movieListAdapter = new MovieListAdapter(context);
		xRecyclerView.setAdapter(movieListAdapter);
            //xRecycle的配置
		xRecyclerView.setPullRefreshEnabled(true);
		xRecyclerView.setLoadingMoreEnabled(true);

		xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {

              refush(type);
			}
			@Override
			public void onLoadMore() {

			loadMore(type);

			}
		});

		changePage(type);


	}

      public void	changePage(int type)
	  {
				for (int i = 0; i <buttons.size() ; i++) {
					if (i==type)
					{
						buttons.get(i).setBackgroundResource(R.drawable.cinema_txt_select);
                        buttons.get(i).setTextColor(Color.parseColor("#ffffff"));

					}else
					{
						buttons.get(i).setBackgroundResource(R.drawable.cinema_txt_default);
						buttons.get(i).setTextColor(Color.parseColor("#333333"));


					}

				}

				mySwitch(type);
				this.type=type;

	  }


     //加载更多
	private void loadMore(int type) {
              isLoadMore=true;
		switch (type){
			case 0:
				page0++;
				mySwitch(0);
				break;
			case 1:
				page1++;
				mySwitch(1);
				break;
			case 2:
				page2++;
				mySwitch(2);
				break;
		}
	}

    //数据请求
	private void	mySwitch(int type){

	   switch (type)
	   {
		   case 0:

			   //热门
			   //进行网络请求

			   Map<String,String> map0=new HashMap<>();
			   map0.put("page", page0+"");
			   map0.put("count", count+"");
			   getString(0, HttpUrl.STRING_HOT_MOVIE,map0,true);

			   break;
		   case 1:
			   //正在热映
			   Map<String,String> map1=new HashMap<>();
			   map1.put("page", page1+"");
			   map1.put("count", count+"");
			   getString(1, HttpUrl.STRING_SHOW_MOVIE,map1,true);
			   break;
		   case 2:
			   //即将上映
			   Map<String,String> map2=new HashMap<>();
			   map2.put("page", page2+"");
			   map2.put("count", count+"");
			   getString(2, HttpUrl.STRING_WILL_MOVIE,map2,true);
			   break;
		   case 3:
			   //搜索

			   break;


	   }
	}

//刷新
private void	refush(int type){
		isRefrush=true;
	switch (type){
		case 0:
			page0=1;
			mySwitch(0);
			break;
		case 1:
			page1=1;
			mySwitch(1);
			break;
		case 2:
			page2=1;
			mySwitch(2);
			break;

	}

}

   //请求陈宫
	@Override
	public void successString(int type, String data) {
		super.successString(type, data);
		switch (type)
		{
			case 0:


			case 1:
				//正在热映

			case 2:
				//即将上映

				if (isLoadMore)
				{
					MovieItem movieItem2 = new Gson().fromJson(data, MovieItem.class);
					mMovieItem.getResult().addAll(movieItem2.getResult());
					movieListAdapter.setData(mMovieItem);
					movieListAdapter.notifyDataSetChanged();
					xRecyclerView.loadMoreComplete();//结束加载
					isLoadMore=false;
					return;
				}

				mMovieItem = new Gson().fromJson(data, MovieItem.class);
				movieListAdapter.setData(mMovieItem);
				movieListAdapter.notifyDataSetChanged();

				if (isRefrush)
				{
					xRecyclerView.refreshComplete();//结束刷新动画
					isRefrush=false;
				}
				break;
			case 3:
				//搜索

				break;


		}
	}

	@Override
	public void error(String error) {
		super.error(error);

		Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
	}

	public void initView(XRecyclerView xRecyclerView, TextView textHot, TextView textNow, TextView textWill) {
	
	 this.xRecyclerView=xRecyclerView;
	 this.textHot=textHot;
	 this.textNow=textNow;
	 this.textWill=textWill;
	}
}

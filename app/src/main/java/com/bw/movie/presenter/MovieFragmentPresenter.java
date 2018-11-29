package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieHeadAdapter;
import com.bw.movie.adapter.MovieItemAdapter;
import com.bw.movie.adapter.MoviePagerAdapter;
import com.bw.movie.adapter.MovieViewPagerAdapter;
import com.bw.movie.model.MovieItem;
import com.bw.movie.model.MoviePage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author 李地坤
 * @date  11/28
 *
 * 首页中的电影页面碎片
 */
public class MovieFragmentPresenter extends AppDelegate{
private List<MoviePage> moviePages;
private Context context;
private XRecyclerView xRecyclerView;
private FragmentManager fragmentManager;
	private MovieHeadAdapter itemAdapter;

	@Override
	public int getLayout() {
		return R.layout.fragment_movie;
	}

	@Override
	public void initContext(Context context) {
		super.initContext(context);
		this.context=context;
	}

	@Override
	public void initData() {
		super.initData();
		//展示数据
      moviePages=new ArrayList<>();
      moviePages.add(new MoviePage("正在热映",HttpUrl.STRING_HOT_MOVIE));
      moviePages.add(new MoviePage("正在上映",HttpUrl.STRING_SHOW_MOVIE));
	  moviePages.add(new MoviePage("即将上映",HttpUrl.STRING_WILL_MOVIE));



		View headView = View.inflate(context, R.layout.head_movie, null);
		RecyclerCoverFlow recycleRotate = headView.findViewById(R.id.recycleRotate);//旋转控件
		itemAdapter = new MovieHeadAdapter(context);

		recycleRotate.setAdapter(itemAdapter);
		Map<String, String> map = new HashMap<>();
		map.put("page", "1");
		map.put("count", "20");
		getString(1,HttpUrl.STRING_HOT_MOVIE,map,false);//请求网络数据


		xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		MoviePagerAdapter moviePagerAdapter = new MoviePagerAdapter(context);
		moviePagerAdapter.setData(moviePages);

		xRecyclerView.setAdapter(moviePagerAdapter);
		xRecyclerView.addHeaderView(headView);

	}


	public void initView(XRecyclerView xRecyclerView, FragmentManager childFragmentManager){

    this.xRecyclerView=xRecyclerView;
    this.fragmentManager=childFragmentManager;
	}

	@Override
	public void successString(int type, String data) {
		super.successString(type, data);
           switch (type)
		   {
			   case 1:
				   MovieItem movieItem = new Gson().fromJson(data, MovieItem.class);
				   itemAdapter.setData(movieItem);
				   itemAdapter.notifyDataSetChanged();
				   break;
		   }

	}
}

package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.MoviePagerAdapter;
import com.bw.movie.model.MoviePage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
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

      moviePages=new ArrayList<>();
      moviePages.add(new MoviePage("正在热映",HttpUrl.STRING_HOT_MOVIE));
      moviePages.add(new MoviePage("正在上映",HttpUrl.STRING_SHOW_MOVIE));
	  moviePages.add(new MoviePage("即将上映",HttpUrl.STRING_WILL_MOVIE));


		View headView = View.inflate(context, R.layout.head_movie, null);


		xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		MoviePagerAdapter moviePagerAdapter = new MoviePagerAdapter(context);
		moviePagerAdapter.setData(moviePages);
		xRecyclerView.setAdapter(moviePagerAdapter);
		xRecyclerView.addHeaderView(headView);
	}

	public void initView(XRecyclerView xRecyclerView){
    this.xRecyclerView=xRecyclerView;

	}

}

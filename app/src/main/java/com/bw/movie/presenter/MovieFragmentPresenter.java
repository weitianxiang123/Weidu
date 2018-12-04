package com.bw.movie.presenter;


import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieHeadAdapter;

import com.bw.movie.adapter.MoviePagerAdapter;

import com.bw.movie.model.MovieItem;
import com.bw.movie.model.MoviePage;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.LocationUtils;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author 李地坤
 * @date  11/28
 *
 * 首页中的电影页面碎片
 */
public class MovieFragmentPresenter extends AppDelegate{
	private boolean isLogin;
	private RootMessage rootMessage;

private List<MoviePage> moviePages;
private Context context;
private XRecyclerView xRecyclerView;
private FragmentManager fragmentManager;
	private MovieHeadAdapter itemAdapter;
	private RecyclerCoverFlow recycleRotate;

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
		isLogin();
		//展示数据
      moviePages=new ArrayList<>();
      moviePages.add(new MoviePage("正在热映",HttpUrl.STRING_HOT_MOVIE));
      moviePages.add(new MoviePage("正在上映",HttpUrl.STRING_SHOW_MOVIE));
	  moviePages.add(new MoviePage("即将上映",HttpUrl.STRING_WILL_MOVIE));

		View headView = View.inflate(context, R.layout.head_movie, null);
		//旋转控件
		recycleRotate = headView.findViewById(R.id.recycleRotate);
		TextView textLocation=headView.findViewById(R.id.textLocation);

		//定位
		Location location = LocationUtils.getInstance(context).getLngAndLat(context);
		Geocoder geocoder;
		if (location!=null)
		{
			geocoder=new Geocoder(context);
			try {
				List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				Address address = addressList.get(0);
				String locality = address.getLocality();

				textLocation.setText(locality);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		itemAdapter = new MovieHeadAdapter(context);
		recycleRotate.setAdapter(itemAdapter);
		Map<String, String> map = new HashMap<>();
		map.put("page", "1");
		map.put("count", "20");
		getString(1,HttpUrl.STRING_HOT_MOVIE,map,false);//请求网络数据


		xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		MoviePagerAdapter moviePagerAdapter = new MoviePagerAdapter(context);
		moviePagerAdapter.setData(moviePages);//设置适配器

		xRecyclerView.setAdapter(moviePagerAdapter);
		xRecyclerView.addHeaderView(headView);//设置头View

		//测试登录

		/*Map<String, RequestBody> map2 = new HashMap<>();

		MediaType mediaType=MediaType.parse("application/x-www-form-urlencoded");

		RequestBody phone=Reques、tBody.create(mediaType,"15033705919");
		RequestBody pwd=RequestBody.create(mediaType,EncryptUtil.encrypt("123qwe"));*/
		/*Map<String, String> map2 = new HashMap<>();
		map2.put("phone","15033705919");
		map2.put("pwd",EncryptUtil.encrypt("123qwe"));

		new HttpHelper(context).lrPost(HttpUrl.STRING_LOGIN,map2).result(new HttpListener() {
			@Override
			public void success(String data) {
				ShareUtil.saveLogin(data,context);
				isLogin();//刷新，存储用户数据
			}
			@Override
			public void fail(String error) {
				Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
			}
		});*/

		//数据持久化

		/*postString(2,HttpUrl.STRING_LOGIN,map2,false);*/

		//测试关注


		/*Toast.makeText(context, ""+isLogin+rootMessage.getResult().getUserInfo().getNickName(), Toast.LENGTH_SHORT).show();*/

	}


	public void initView(XRecyclerView xRecyclerView, FragmentManager childFragmentManager){

    this.xRecyclerView=xRecyclerView;
    this.fragmentManager=childFragmentManager;
	}

	//网络请求成功返回
	@Override
	public void successString(int type, String data) {
		super.successString(type, data);
           switch (type)
		   {
			   case 1:
				   MovieItem movieItem = new Gson().fromJson(data, MovieItem.class);
				   itemAdapter.setData(movieItem);
				   itemAdapter.notifyDataSetChanged();
				   recycleRotate.scrollToPosition(movieItem.getResult().size()/2);

				   break;
			   case 2:
				   Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
			   	break;
			   case 3:
				   Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
		   }

	}

	@Override
	public void error(String error) {
		super.error(error);
		Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();

	}


	//获取用户数据
	@Override
	public void rootMessage(boolean isLogin, RootMessage rootMessage) {
		super.rootMessage(isLogin, rootMessage);
      this.isLogin=isLogin;
      this.rootMessage=rootMessage;

	}
}

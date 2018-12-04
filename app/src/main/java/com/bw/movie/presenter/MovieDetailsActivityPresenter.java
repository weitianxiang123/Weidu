package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.model.MovieEvaluate;
import com.bw.movie.adapter.MovieAdvandeAdapter;
import com.bw.movie.adapter.MovieEvaluateAdapter;
import com.bw.movie.adapter.MoviePeopleEvaluateAdapter;
import com.bw.movie.adapter.MovieStagePhotoAdapter;
import com.bw.movie.model.MovieDetatil;
import com.bw.movie.model.MoviePeopleEvaluate;
import com.bw.movie.model.RootMessage;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;

public class MovieDetailsActivityPresenter extends AppDelegate implements View.OnClickListener {
	private Context context;
	private boolean isLogin;
	private RootMessage rootMessage;

	TextView name1; //顶部电影详情

	ImageView hard;//关注红心

	TextView name2;//电影名称

	SimpleDraweeView imageMovie;//大图

	TextView textDetails;//详情

	TextView textAdvanceNotice;//预告

	TextView textStagePhoto;//剧照

	TextView textEvaluate;//影评

	TextView textBuy;//buy
	private MovieDetatil movieDetatil;

	private RelativeLayout layoutFather;
	private PopupWindow popupWindow;
	private RelativeLayout relativeLayout;
	private View fatherView;
	private MovieEvaluateAdapter movieEvaluateAdapter;
	private MoviePeopleEvaluateAdapter moviePeopleEvaluateAdapter;

	private final int EVALUATE=2; //1，3已经被使用
	private final int PEOPLE_EVALUATE=4;
	private final int SEND_EVALUATE=5;
	private final int SEND_PEOPLE_EVALUATE=6;
	private MovieEvaluate movieEvaluate;
	private Activity activity;
	private int movieId;
	private int page;
	private int peoplePage;
	private int count;
	private int peopleEvaluateId;
    private boolean isLoadMore;
    private boolean isRefrush;
	private XRecyclerView evaluateRecycle;

	@Override
	public int getLayout() {
		return R.layout.activity_movie_details;
	}

	@Override
	public void initData() {
		super.initData();

		isLogin();

		//获取电影信息
		activity = (Activity) context;
		Intent intent = activity.getIntent();
		movieId = intent.getIntExtra("movieId", 1);



		//获取电影信息
		Map<String, String> map3 = new HashMap<>();
		map3.put("movieId", "" + movieId);
		getString(1, HttpUrl.STRING_DETAIL_MOVIE, map3, true);


		//父popWindow
		fatherView = View.inflate(context, R.layout.pop_movie_ditails, null);
		fatherView.findViewById(R.id.imageCancel).setOnClickListener(this);
		fatherView.findViewById(R.id.viewNull).setOnClickListener(this);
		popupWindow = new PopupWindow(fatherView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);



	}

	//展示影评页面
	public void showEvaluatePop(){
		page = 1;
		peoplePage = 1;
		count = 9;

		View showView=View.inflate(context,R.layout.view_evaluate_movie,null);
		TextView title = showView.findViewById(R.id.textAdvance);
		ImageView imageEvaluateBottom= showView.findViewById(R.id.imageEvaluateBottom);
		LinearLayout layoutEdit= showView.findViewById(R.id.layoutEdit);
		EditText editEvaluate= showView.findViewById(R.id.editEvaluate);
		TextView textSend= showView.findViewById(R.id.textSend);//发送

		imageEvaluateBottom.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				imageEvaluateBottom.setVisibility(View.GONE);
				layoutEdit.setVisibility(View.VISIBLE);
				/*showSoftInputFromWindow(activity,editEvaluate);//弹出键盘,没什么卵用*/
			}
		});
        textSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//发送评论
				String editStr = editEvaluate.getText().toString();
				if (TextUtils.isEmpty(editStr))
				{
					Toast.makeText(context, "评论不能为空", Toast.LENGTH_SHORT).show();
				}else
				{
					//发送评论

					//登陆后执行网络请求
					if (isLogin)
					{
						Map<String,String> fMap=new HashMap<>();
						fMap.put("movieId",movieDetatil.getResult().getId()+"");
						fMap.put("commentContent",editStr);
						Toast.makeText(context, ""+movieDetatil.getResult().getId(), Toast.LENGTH_SHORT).show();
						new HttpHelper(context).lrHead( HttpUrl.STRING_ADD_EVALUATE_MOVIE,fMap,null).result(new HttpListener() {
							@Override
							public void success(String data) {
                                //请求成功
								Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
								//网络请求
								Map<String,String> map=new HashMap<>();
								map.put("movieId",movieId+"");
								map.put("page",""+page);
								map.put("count",""+count);
								getString(EVALUATE,HttpUrl.STRING_EVALUATE_MOVIE,map,true);
							}

							@Override
							public void fail(String error) {
								Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
							}
						});

						page =1;
						imageEvaluateBottom.setVisibility(View.VISIBLE);
						layoutEdit.setVisibility(View.GONE);
					}else
					{
                       //去登陆
						context.startActivity(new Intent(context,LoginActivity.class));
					}
				/*	postString(SEND_EVALUATE, HttpUrl.STRING_ADD_EVALUATE_MOVIE,map,true);*/

				}


			}
		});
		title.setText("影评");

		//电影评论
		evaluateRecycle = showView.findViewById(R.id.recycleMovie);
		evaluateRecycle.setLoadingMoreEnabled(true);
		evaluateRecycle.setPullRefreshEnabled(true);
		evaluateRecycle.setLayoutManager(new LinearLayoutManager(context));
		movieEvaluateAdapter = new MovieEvaluateAdapter(context);
		evaluateRecycle.setAdapter(movieEvaluateAdapter);
        //下拉刷新和上拉加载
		evaluateRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
               page=1;
               isRefrush=true;
				Map<String,String> map=new HashMap<>();
				map.put("movieId",movieId+"");
				map.put("page",""+ page);
				map.put("count",""+ count);
				getString(EVALUATE,HttpUrl.STRING_EVALUATE_MOVIE,map,true);
			}
			@Override
			public void onLoadMore() {
               page++;
               isLoadMore=true;
				Map<String,String> map=new HashMap<>();
				map.put("movieId",movieId+"");
				map.put("page",""+ page);
				map.put("count",""+ count);
				getString(EVALUATE,HttpUrl.STRING_EVALUATE_MOVIE,map,true);
			}
		});
        //网络请求
		Map<String,String> map=new HashMap<>();
		map.put("movieId",movieId+"");
		map.put("page",""+ page);
		map.put("count",""+ count);
		getString(EVALUATE,HttpUrl.STRING_EVALUATE_MOVIE,map,true);

		//用户评论
		View showPeopleView=View.inflate(context,R.layout.view_evaluate_movie,null);
		XRecyclerView evaluatePeopleRecycle= showPeopleView.findViewById(R.id.recycleMovie);
		ImageView imageEvaluateBottomPeople=showPeopleView.findViewById(R.id.imageEvaluateBottom);
		LinearLayout layoutEditPeople= showPeopleView.findViewById(R.id.layoutEdit);
		EditText editEvaluatePeople= showPeopleView.findViewById(R.id.editEvaluate);
		TextView textSendPeople= showPeopleView.findViewById(R.id.textSend);//发送

		evaluatePeopleRecycle.setLayoutManager(new LinearLayoutManager(context));
		moviePeopleEvaluateAdapter = new MoviePeopleEvaluateAdapter(context);
		evaluatePeopleRecycle.setAdapter(moviePeopleEvaluateAdapter);

        ImageView imageView= showPeopleView.findViewById(R.id.imageBack);//back按钮
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 RelativeLayout relativeLayout = fatherView.findViewById(R.id.layoutAdd);
				 relativeLayout.removeAllViews();
				 relativeLayout.addView(showView);
			 }
		 });

         imageEvaluateBottomPeople.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 imageEvaluateBottomPeople.setVisibility(View.GONE);
				 layoutEditPeople.setVisibility(View.VISIBLE);
				/* showSoftInputFromWindow(activity,editEvaluatePeople);//弹出键盘*/
			 }
		 });

         //发送对用户的评论
		textSendPeople.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				String editStr = editEvaluatePeople.getText().toString();
				if (TextUtils.isEmpty(editStr))
				{
					Toast.makeText(context, "评论不能为空", Toast.LENGTH_SHORT).show();
				}else
				{
					//登陆后执行网络请求
					if (isLogin)
					{
						Map<String,String> fMap=new HashMap<>();
						fMap.put("commentId",peopleEvaluateId+"");
						fMap.put("replyContent",editStr);
						new HttpHelper(context).lrHead( HttpUrl.STRING_ADD_PEOPLE_EVALUATE_MOVIE,fMap,null).result(new HttpListener () {
							@Override
							public void success(String data) {
								//请求成功
								Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
								//网络请求
								Map<String,String> map2=new HashMap<>();
								map2.put("commentId",peopleEvaluateId+"");
								map2.put("page",""+ peoplePage);
								map2.put("count",""+ count);
								getString(PEOPLE_EVALUATE,HttpUrl.STRING_PEOPLE_EVALUATE_MOVIE,map2,true);
							}

							@Override
							public void fail(String error) {
								Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
							}
						});

						peoplePage =1;
						imageEvaluateBottomPeople.setVisibility(View.VISIBLE);
						layoutEditPeople.setVisibility(View.GONE);
					}else
					{
						//去登陆
						context.startActivity(new Intent(context,LoginActivity.class));
					}
					/*//发送评论
					Map<String,String> map=new HashMap<>();
					map.put("commentId",peopleEvaluateId+"");
					map.put("replyContent",editStr);

					//执行网络请求
					postString(SEND_PEOPLE_EVALUATE, HttpUrl.STRING_ADD_PEOPLE_EVALUATE_MOVIE,map,true);*/
				}


			}
		});
		movieEvaluateAdapter.setListener(new MovieEvaluateAdapter.ClickEvaluateListener() {
			@Override
			public void clickEvaluate(int id) {
				peopleEvaluateId = id;
				//替换布局
				RelativeLayout relativeLayout = fatherView.findViewById(R.id.layoutAdd);
				relativeLayout.removeAllViews();
				relativeLayout.addView(showPeopleView);

				//网络请求
				Map<String,String> map=new HashMap<>();

				map.put("commentId",peopleEvaluateId+"");
				map.put("page",""+ peoplePage);
				map.put("count",""+ count);
				getString(PEOPLE_EVALUATE,HttpUrl.STRING_PEOPLE_EVALUATE_MOVIE,map,true);
			}
		});

		showPop(fatherView, showView);

	}


	//刷新页面数据
	public void refushUserData(){

		isLogin();
	}

	//弹出软键盘
	public  void showSoftInputFromWindow(Activity activity, EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}


	//展示pop
	public void showPop(View fatherView, View childView) {

		RelativeLayout relativeLayout = fatherView.findViewById(R.id.layoutAdd);
		relativeLayout.removeAllViews();
		relativeLayout.addView(childView);
		popupWindow.showAtLocation(layoutFather, Gravity.BOTTOM, 0, 0);

		//更改UI绑定的数据
		if (movieDetatil == null)
			return;
		name1.setText(movieDetatil.getResult().getName());

	}

	//取消显示
	public void cancelPop() {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
			name1.setText("电影详情");

			Jzvd.releaseAllVideos();
		}
	}

	//展示剧照
  public  void showStagephotoPop(){
	  View shoeView=View.inflate(context,R.layout.view_advance_movie,null);
	  TextView title = shoeView.findViewById(R.id.textAdvance);
	  title.setText("剧照");
	  RecyclerView recyclerMovie= shoeView.findViewById(R.id.recycleMovie);
	  recyclerMovie.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
	  MovieStagePhotoAdapter stagePhotoAdapter = new MovieStagePhotoAdapter(context);
	  recyclerMovie.setAdapter(stagePhotoAdapter);
	  List<String> posterList = movieDetatil.getResult().getPosterList();
	  stagePhotoAdapter.setData(posterList);
	  stagePhotoAdapter.notifyDataSetChanged();

	  showPop(fatherView, shoeView);

  }

	//展示预告
	public void showAdvancePop(){
		View shoeView=View.inflate(context,R.layout.view_advance_movie,null);
		TextView title = shoeView.findViewById(R.id.textAdvance);
		title.setText("预告");
		RecyclerView recyclerMovie= shoeView.findViewById(R.id.recycleMovie);
        recyclerMovie.setLayoutManager(new LinearLayoutManager(context));
		MovieAdvandeAdapter advandeAdapter = new MovieAdvandeAdapter(context);
		recyclerMovie.setAdapter(advandeAdapter);
		List<MovieDetatil.ResultBean.ShortFilmListBean> shortFilmList = movieDetatil.getResult().getShortFilmList();
		advandeAdapter.setData(shortFilmList);
		advandeAdapter.notifyDataSetChanged();

		showPop(fatherView, shoeView);

	}




	//展示详情
	public void showDitailsPop() {
		View showView = View.inflate(context, R.layout.view_detial_movie, null);
		SimpleDraweeView image = showView.findViewById(R.id.imagePhoto);
		TextView type= showView.findViewById(R.id.textType);
		TextView direct= showView.findViewById(R.id.textDirect);
		TextView time= showView.findViewById(R.id.textTime);
		TextView from= showView.findViewById(R.id.textFrom);
		TextView actor= showView.findViewById(R.id.textActorText);
		TextView easy= showView.findViewById(R.id.textEasyText);

		MovieDetatil.ResultBean result = movieDetatil.getResult();
		String imageUrl = result.getImageUrl();
		String director = result.getDirector();
		String movieTypes = result.getMovieTypes();
		String duration = result.getDuration();
		String placeOrigin = result.getPlaceOrigin();
		String starring = result.getStarring();
		String summary = result.getSummary();

		image.setImageURI(imageUrl);
		type.setText(movieTypes);
		direct.setText(director);
		time.setText(duration);
		from.setText(placeOrigin);
		actor.setText(starring);
		easy.setText(summary);




		showPop(fatherView, showView);
	}


	//请求成功返回的数据
	@Override
	public void successString(int type, String data) {
		super.successString(type, data);

		switch (type) {
			case 3:
				Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
				break;
			case 1:
				movieDetatil = new Gson().fromJson(data, MovieDetatil.class);
				//绘制UI
				drawPage(movieDetatil);
				break;
			case EVALUATE:
				if (isLoadMore)
				{//上拉加载
					MovieEvaluate movie = new Gson().fromJson(data, MovieEvaluate.class);
					movieEvaluate.getResult().addAll(movie.getResult());
					movieEvaluateAdapter.setData(movieEvaluate);
					movieEvaluateAdapter.notifyDataSetChanged();
					isLoadMore=false;
                     evaluateRecycle.loadMoreComplete();
                     return;
				}
				movieEvaluate = new Gson().fromJson(data, MovieEvaluate.class);
				movieEvaluateAdapter.setData(movieEvaluate);
				movieEvaluateAdapter.notifyDataSetChanged();
				if (isRefrush)  //上拉刷新
				{
					evaluateRecycle.refreshComplete();
				}

				break;
			case PEOPLE_EVALUATE:
				if (data.contains("无数据")&&data.contains("message")&&data.contains("0000"))
				{
					Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
				}
				else
				{ MoviePeopleEvaluate moviePeopleEvaluate = new Gson().fromJson(data, MoviePeopleEvaluate.class);
				moviePeopleEvaluateAdapter.setData(moviePeopleEvaluate);
				moviePeopleEvaluateAdapter.notifyDataSetChanged();
				}

				break;

			case SEND_EVALUATE:

				break;

			case SEND_PEOPLE_EVALUATE:

				break;

		}
	}
	//当back按下，pop将何去何从
   public boolean	popOnBackDown(){
         if (popupWindow.isShowing())
		 {
		 	cancelPop();
		 	return true;
		 }
        return  false;
	}

	//填充页面内容
	private void drawPage(MovieDetatil movieDetatil) {
		if (movieDetatil != null) {
			MovieDetatil.ResultBean result = movieDetatil.getResult();
			String imageUrl = result.getImageUrl();
			String name = result.getName();


			name2.setText(name);
			imageMovie.setImageURI(imageUrl);
			drawHart(movieDetatil);

		}

	}

	//绘制是否关注红心
	public void drawHart(MovieDetatil movieDetatil) {
		if (movieDetatil != null) {
			MovieDetatil.ResultBean result = movieDetatil.getResult();
			boolean followMovie = result.isFollowMovie();
			if (!followMovie)//true为未关注
			{
				hard.setImageResource(R.drawable.com_icon_collection_selected);
			} else {
				hard.setImageResource(R.drawable.com_icon_collection_default);
			}
		}

	}

	//关注，取消关注
	public void giveHart() {

		if (!isLogin) {//登陆了才可以点关注,跳转登陆页
			context.startActivity(new Intent(context, LoginActivity.class));
			return;
		}

		Map<String, String> map = new HashMap<>();
		map.put("movieId", movieDetatil.getResult().getId() + "");
		if (movieDetatil != null && movieDetatil.getResult().isFollowMovie())//true为为关注
		{
			//设置关注
			movieDetatil.getResult().setFollowMovie(false);
			getString(3, HttpUrl.STRING_ATTENTION_MOVIE, map, true);
		} else if (movieDetatil != null)//取消关注
		{
			movieDetatil.getResult().setFollowMovie(true);
			getString(3, HttpUrl.STRING_CANCLE_ATTENTION_MOVIE, map, true);
		}

		drawHart(movieDetatil);
	}


	@Override
	public void initContext(Context context) {
		super.initContext(context);
		this.context = context;
	}

	public void initView(TextView name1, ImageView hard, TextView name2, SimpleDraweeView imageMovie, TextView textDetails, TextView textAdvanceNotice, TextView textStagePhoto, TextView textEvaluate, TextView textBuy, RelativeLayout layoutFather) {
		this.name1 = name1;
		this.hard = hard;
		this.name2 = name2;
		this.imageMovie = imageMovie;
		this.textDetails = textDetails;
		this.textAdvanceNotice = textAdvanceNotice;
		this.textStagePhoto = textStagePhoto;
		this.textEvaluate = textEvaluate;
		this.layoutFather = layoutFather;
	}


	@Override
	public void rootMessage(boolean isLogin, RootMessage rootMessage) {
		super.rootMessage(isLogin, rootMessage);
		this.isLogin = isLogin;
		this.rootMessage = rootMessage;
	}

	@Override
	public void error(String error) {
		super.error(error);
		Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imageCancel:
				cancelPop();
				break;
			case R.id.viewNull:
				cancelPop();
				break;


		}
	}
}

package com.bw.movie.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.NetNoneActivity;
import com.bw.movie.model.RootMessage;
import com.bw.movie.utils.NetUtils;
import com.bw.movie.utils.ShareUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpHelper {
    private Context context;
    // 联网工具类,待修改
    private BaseService mbBaseService;
    private static String BASE_URL = "http://mobile.bwstudent.com/movieApi/";
    private boolean isLogin;
    private RootMessage rootMessage;
    private String sessionId;
    private int userId;
    private String url;

    public HttpHelper(Context context){
        this.context=context;

        //获取用户登陆信息
         isLogin();
          if (isLogin)
          {
              sessionId = rootMessage.getResult().getSessionId();
              userId = rootMessage.getResult().getUserId();
          }
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mbBaseService = retrofit.create(BaseService.class);
    }

    //mineGet
    public HttpHelper mineGet(String url, Map<String, String> map, Map<String, String> headMap) {
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }

        if (map == null) {
            map = new HashMap<>();
        }
        if (headMap == null) {
            headMap = new HashMap<>();
        }
        if (isLogin){
            RootMessage.ResultBean result = ShareUtil.getRootMessage(context).getResult();
            headMap.put("userId",result.getUserId()+"");
            headMap.put("sessionId",result.getSessionId());
            mbBaseService.mineGet(url, map, headMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else {
            Toast.makeText(context, "用户当前没有登录", Toast.LENGTH_SHORT).show();
        }


        return this;
    }
    //mineGet
    public HttpHelper minePost(String url, Map<String, String> map, Map<String, String> headMap) {

        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }
        if (map == null) {
            map = new HashMap<>();
        }
        if (headMap == null) {
            headMap = new HashMap<>();
        }
        if (isLogin){
            RootMessage.ResultBean result = ShareUtil.getRootMessage(context).getResult();
            headMap.put("userId",result.getUserId()+"");
            headMap.put("sessionId",result.getSessionId());
            mbBaseService.minePost(url, map, headMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else {
            Toast.makeText(context, "用户当前没有登录", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    // Get请求
    public HttpHelper get(String url, Map<String,String> map,boolean weatherHead){
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }
        if (map==null){
            map = new HashMap<>();
        }

        if (weatherHead&&isLogin)
        {
            //包含请求头，登录状态为已登录
            if (rootMessage!=null)
            {
                weatherHead(url,map,false);
                return this;
            }
        }
        mbBaseService.get(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return this;
    }

    // Post请求
    public HttpHelper post(String url, Map<String,String> map,boolean weatherHead){
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }
        if (map==null){
            map = new HashMap<>();
        }

        if (weatherHead&&isLogin)
        {
            //包含请求头，登录状态为已登录
            if (rootMessage!=null)
            {

             weatherHead(url,map,true);
                return this;
            }
        }
      /*  Log.i("HttpHelper","weatherHead,我没有执行"+url);*/
        mbBaseService.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }


    //执行带请求头的请求
    public void weatherHead(String url,Map<String,String> map,boolean isPost){

        String sessionId = rootMessage.getResult().getSessionId();
        int userId = rootMessage.getResult().getUserId();
        if (isPost)
        {
            mbBaseService.headPost(url,map,userId,sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else
        {

            Log.i("HttpHelper","weatherHead,我执行了");
            mbBaseService.headGet(url,map,userId,sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    /**
     *
     * @param url
     * @param fMap
     * @param map
     * @return
     *
     *  //执行带请求头的请求
     *  这个必须要请求头，必须在登陆状态下使用
     *
     */
    public HttpHelper lrHead(String url,Map<String,String> fMap,Map<String,String> map){
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }
        if (fMap==null)
            fMap=new HashMap<>();
        if (map==null)
            map=new HashMap<>();


        if (true)
        {
            mbBaseService.lrHeadPost(url,fMap,map,userId,sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else
        {

            Log.i("HttpHelper","weatherHead,我执行了");
            mbBaseService.headGet(url,map,userId,sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
            //  get未更新
        }

        return this;
    }


    public HttpHelper upHeadImage(String url, MultipartBody.Part part){


        if (true)
        {
            mbBaseService.upHead(url,userId,sessionId,part)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else
        {

     /*       Log.i("HttpHelper","weatherHead,我执行了");
            mbBaseService.headGet(url,map,userId,sessionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);*/
            //  get未更新
        }

        return this;
    }

    public HttpHelper lrPost(String url,Map<String, String> map)
    {
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {
            this.url=url;
            return  this;
        }
        if (map==null){
            map = new HashMap<>();
        }
        mbBaseService.lrPost(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    // 观察者
    private Observer observer = new Observer<ResponseBody>(){
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                listener.success(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String error = e.getMessage();
            listener.fail(error);
        }

        @Override
        public void onComplete() {

        }
    };


    // 传递接口
    private HttpListener listener;

    public void result(HttpListener listener){
        //判断是否有网络
        if (NetUtils.getNetWorkState(context)==NetUtils.NETWORK_NONE)
        {//如果没有网
            Log.i("saveUtil","没有网络啊");
            if (!(context instanceof MainActivity))
            {//如果不是MainActivity
                context.startActivity(new Intent(context, NetNoneActivity.class));
                listener.fail("没有网络哦");
                Activity activity=(Activity)context;
                activity.finish();
            }else
            {

                String s = HttpSaveUtil.get(url);
                if (s!=null)
                listener.success(s);
                else
                listener.fail("网络开小差了");
            }
        }else
        {
            this.listener = listener;
        }

    }





    public void isLogin(){
        isLogin= ShareUtil.isLogin(context);
        if (isLogin)
        {//用户已经登录
            rootMessage = ShareUtil.getRootMessage(context);
        }else
        {
            rootMessage=null;
        }

    }
}

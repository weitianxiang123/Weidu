package com.bw.movie.net;

import android.content.Context;
import android.util.Log;

import com.bw.movie.model.RootMessage;
import com.bw.movie.utils.ShareUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    public HttpHelper(Context context){
        this.context=context;

        //获取用户登陆信息
         isLogin();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mbBaseService = retrofit.create(BaseService.class);
    }

    // Get请求
    public HttpHelper get(String url, Map<String,String> map,boolean weatherHead){
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


    public HttpHelper lrPost(String url,Map<String, String> map)
    {
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
        this.listener = listener;
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

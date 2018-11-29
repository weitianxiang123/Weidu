package com.bw.movie.net;

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
    // 联网工具类,待修改
    private BaseService mbBaseService;
    private static String BASE_URL = "http://mobile.bwstudent.com/movieApi/";
    public HttpHelper(){
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

        if (weatherHead)
        {
            //包含请求头

         return this;
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

        if (weatherHead)
        {
            //包含请求头

            return this;
        }
        mbBaseService.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
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
}

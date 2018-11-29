package com.bw.movie.net;

import java.util.Map;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseService {



    @GET
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,String> map);


    @GET
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> headGet(@Url String url, @QueryMap Map<String,String> map,@Header("userId") String userId,@Header("sessionId") String sessionId);

    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String,String> map);


    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> headPost(@Url String url, @QueryMap Map<String,String> map,@Header("userId") String userId,@Header("sessionId") String sessionId);

}

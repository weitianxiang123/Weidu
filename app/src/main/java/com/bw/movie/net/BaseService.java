package com.bw.movie.net;

import com.bw.movie.utils.ShareUtil;

import java.util.Map;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseService {



    @GET
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,String> map);


    @GET
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> headGet(@Url String url, @QueryMap Map<String,String> map,@Header("userId") int userId,@Header("sessionId") String sessionId);

    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String,String> map);


    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> headPost(@Url String url, @QueryMap Map<String,String> map,@Header("userId") int userId,
                                      @Header("sessionId") String sessionId);



    //上传头像
    @Multipart
    @POST
    @Headers("ak:0110010010000")
    Observable<ResponseBody> upHead(@Url String url, @Header("userId") int userId,
                                    @Header("sessionId") String sessionId, @Part MultipartBody.Part part);

  /*  @POST
    @Multipart
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> lrPost(@Url String url, @PartMap Map<String, RequestBody> map);*/

    @FormUrlEncoded
    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> lrPost(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    @Headers({"ak:0110010010000","Content-Type:application/x-www-form-urlencoded"})
    Observable<ResponseBody> lrHeadPost(@Url String url, @FieldMap Map<String, String> fMap,@QueryMap Map<String,String> map,@Header("userId") int userId, @Header("sessionId") String sessionId);


    @GET
    Observable<ResponseBody> mineGet(@Url String url, @QueryMap Map<String, String> map, @HeaderMap Map<String, String> headMap);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> minePost(@Url String url, @FieldMap Map<String, String> map, @HeaderMap Map<String, String> headMap);
}

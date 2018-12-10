package com.bw.movie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 监听网络状态的工具类
 */
public class NetUtils {
  /**
   * 无网络
   */
  public static final int NETWORK_NONE=0;
  /**
   *wifi网络
   */
  public static final int NETWORK_WIFI=1;
  /**
   * 移动网络
   */
  public static final int NETWORK_MOBILE=2;
  /**
   * 网络判断工具
   * @param context
   * @return 0 无网络 1 wifi网络 2移动网络
   */
  public static int getNetWorkState(Context context){
  ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  
//Wifi

    NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

    if(state== NetworkInfo.State.CONNECTED||state== NetworkInfo.State.CONNECTING)
    {
      return NETWORK_WIFI;
    }

    //移动网络

    state=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

    if(state== NetworkInfo.State.CONNECTED||state== NetworkInfo.State.CONNECTING){
      return  NETWORK_MOBILE;
    }
    return NETWORK_NONE;
  }
}

package com.bw.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bw.movie.model.RootMessage;
import com.google.gson.Gson;

public class ShareUtil {

	private static String LOGIN="loginMessage";
	private static String LOGIN_WEATHER="weatherLogin";
	private static String ROOT_KEY="rootMessage";


	public static void saveLogin(String data, Context context){
		//用户登陆json数据的持久化
		SharedPreferences sp = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
		sp.edit().putString(ROOT_KEY,data).putBoolean(LOGIN_WEATHER,true).commit();

	}

	public static void unLogin(Context context){
		//注销用户登陆
		SharedPreferences sp = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
		sp.edit().putBoolean(LOGIN_WEATHER,false).commit();
	}

	public static boolean isLogin(Context context){
		//判断用户是否登录
		if (context==null)
			return false;
		SharedPreferences sp = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
		boolean b = sp.getBoolean(LOGIN_WEATHER, false);
		return b;
	}

	public static String getRootData(Context context){
         //获取用户登录的信息
		if (isLogin(context))
		{
			SharedPreferences sp = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
			String rootMessage = sp.getString(ROOT_KEY, null);
			return  rootMessage;
		}
		return null;
	}


	public static RootMessage getRootMessage(Context context){
		//获取用户信息的bean
		if (getRootData(context)!=null)
		{
			RootMessage rootMessage = new Gson().fromJson(getRootData(context), RootMessage.class);
			return rootMessage;
		}
		return null;
	}
}

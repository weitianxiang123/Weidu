package com.bw.movie.net;

import android.util.Log;

import com.bw.movie.application.App;
import com.bw.movie.greendao.DataAllSaveDao;
import com.bw.movie.model.DataAllSave;

public class HttpSaveUtil {


	public static void save(String url, String data) {


		Log.i("saveUtil", "接口想要持久化"+url);
		int id = getId(url);
		if (id == 0)
			return;

		DataAllSaveDao dataAllSaveDao = App.getInstance().getDataAllSaveDao();
		DataAllSave save = new DataAllSave((long) id, data);


		Log.i("saveUtil", "这个数据存在吗"+dataAllSaveDao.load((long) id));

		//如果数据存在就修改，如果不存在就添加数据
		if (dataAllSaveDao.load((long) id)!=null)
			dataAllSaveDao.update(save);
		else
			dataAllSaveDao.insert(save);




	}


	public static String get(String url) {

		int id = getId(url);
		DataAllSaveDao dataAllSaveDao = App.getInstance().getDataAllSaveDao();
		DataAllSave save = dataAllSaveDao.load((long) id);

		if (save == null) {
			Log.i("saveUtil", "没有找到，但是有Id");
			return null;
		} else {
			Log.i("saveUtil", "获取了持久化数据"+save.getData());
			return save.getData();
		}
	}

	private static int getId(String url) {

		if (url.equals(HttpUrl.STRING_HOT_MOVIE)) {
			return 1;
		}
		if (url.equals(HttpUrl.STRING_SHOW_MOVIE))
		{
           return 2;
		}

		if (url.equals(HttpUrl.STRING_WILL_MOVIE))
		{
			return 3;
		}

		return 0;
	}

}

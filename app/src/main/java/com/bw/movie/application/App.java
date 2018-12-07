package com.bw.movie.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bw.movie.greendao.DaoMaster;
import com.bw.movie.greendao.DaoSession;
import com.bw.movie.greendao.DataAllSaveDao;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者：owner on 2018/11/28 11:39
 */
public class App extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DataAllSaveDao dataAllSaveDao;
    private static   App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
        Fresco.initialize(this);
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxb3852e6a6b7d9516 ");
        setDatabase();



    }

    private static App mainApp;

    public static IWXAPI mWxApi;
    public static App getInstance(){
        return instance;
    }

    //初始化数据库
    private void setDatabase() {

        mHelper = new DaoMaster.DevOpenHelper(this, "dytd-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        dataAllSaveDao = mDaoSession.getDataAllSaveDao();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    public DataAllSaveDao getDataAllSaveDao() {
        return dataAllSaveDao;
    }
}

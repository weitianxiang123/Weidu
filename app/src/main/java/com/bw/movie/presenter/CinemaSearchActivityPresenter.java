package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaSearchAdapter;
import com.bw.movie.mvp.model.CinemaBean;
import com.bw.movie.mvp.model.CinemaSearchBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpUrl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 搜索影片的act
* */
public class CinemaSearchActivityPresenter extends AppDelegate implements SearchView.OnQueryTextListener {
    private Context context;
    private SearchView searchView;
    private XRecyclerView mSearchCinemaListCinemaList;
    private String searchString;
    private int page = 1;
    private List<CinemaSearchBean.ResultBean> list = new ArrayList<>();
    private CinemaSearchAdapter searchAdapter;
    @Override
    public int getLayout() {
        return R.layout.activity_cinema_search;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(SearchView searchView, XRecyclerView mSearchCinemaListCinemaList, String searchString) {
        this.searchView = searchView;
        this.mSearchCinemaListCinemaList= mSearchCinemaListCinemaList;
        this.searchString = searchString;
    }

    @Override
    public void initData() {
        super.initData();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(searchString);
        searchView.setOnQueryTextListener(this);
        // 适配
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchCinemaListCinemaList.setLayoutManager(manager);
        searchAdapter = new CinemaSearchAdapter(context);
        mSearchCinemaListCinemaList.setAdapter(searchAdapter);
        // 请求搜索接口   这里待修改
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("count",10+"");
        map.put("cinemaName",searchString);
        getBean(0, HttpUrl.CINEMA_SEARCH_ALL,map, CinemaSearchBean.class,false);

    }

    @Override
    public <T> void successBean(int type, T bean) {
        super.successBean(type, bean);
        switch (type){
            case 0:
                CinemaSearchBean searchBean = (CinemaSearchBean) bean;
                list = searchBean.getResult();
                searchAdapter.setList(list);
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        //searchString = s;
        /*if (TextUtils.isEmpty(s.trim())){
            Toast.makeText(context,"输入为空")
            return true;
        }*/
        Map<String, String> map = new HashMap<>();
        map.put("page",page+"");
        map.put("count",10+"");
        map.put("cinemaName",s);
        getBean(0, HttpUrl.CINEMA_SEARCH_ALL,map, CinemaSearchBean.class,false);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}

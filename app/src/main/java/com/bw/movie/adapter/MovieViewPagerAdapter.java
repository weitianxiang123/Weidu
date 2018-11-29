package com.bw.movie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 李地坤
 * @date  11/28
 * 电影页面 ViewPager适配器
 */
public class MovieViewPagerAdapter  extends FragmentPagerAdapter{
     List<Fragment> list;
	public MovieViewPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.list=list;
	}
	@Override
	public Fragment getItem(int i) {
		return list.get(i);
	}

	@Override
	public int getCount() {
		return list.size();
	}
}

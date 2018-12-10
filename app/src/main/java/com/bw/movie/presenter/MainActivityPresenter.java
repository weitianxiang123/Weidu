package com.bw.movie.presenter;

import android.content.Context;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.MineFragment;
import com.bw.movie.fragment.MovieFragment;
import com.bw.movie.mvp.view.AppDelegate;

import butterknife.OnClick;


public class MainActivityPresenter extends AppDelegate {
    private boolean cinemaButton = false;
    private boolean movieButton = false;
    private boolean mineButton = false;

    private final int CINEMA = 1;
    private final int MOVIE = 2;
    private final int MINE = 3;

    private Context context;
    private ImageView cinema, movie, mine;
    private FrameLayout contentView;
    private MainActivity activity;
    FragmentManager fragmentManager;
    private MineFragment mineFragment;
    private CinemaFragment cinemaFragment;
    private MovieFragment movieFragment;


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }


    public void initView(ImageView cinema, ImageView movie, ImageView mine, FrameLayout contentView) {
        this.cinema = cinema;
        this.movie = movie;
        this.mine = mine;
        this.contentView = contentView;

    }

    @Override
    public void initData() {
        super.initData();
        activity = (MainActivity) context;
        mineFragment = new MineFragment();
        cinemaFragment = new CinemaFragment();
        movieFragment = new MovieFragment();
        fragmentManager = activity.getSupportFragmentManager();
        // 黄玉冬修改处
        fragmentManager.beginTransaction().add(R.id.contentView,mineFragment).add(R.id.contentView,cinemaFragment).add(R.id.contentView,movieFragment).commit();
        toMovie();
        selectButton(MOVIE);
    }

    // 跳转方法
    public void toMine() {
        fragmentManager.beginTransaction().show(mineFragment).hide(cinemaFragment).hide(movieFragment).commit();
        //fragmentManager.beginTransaction().replace(R.id.contentView, mineFragment).commit();

    }

    // 跳转方法
    public void toMovie() {
        fragmentManager.beginTransaction().show(movieFragment).hide(cinemaFragment).hide(mineFragment).commit();
        //fragmentManager.beginTransaction().replace(R.id.contentView, movieFragment).commit();
    }

    public void toCinema() {
        fragmentManager.beginTransaction().show(cinemaFragment).hide(movieFragment).hide(mineFragment).commit();
        //fragmentManager.beginTransaction().replace(R.id.contentView, cinemaFragment).commit();
    }





    //设置选中的Button
    public void selectButton(int button) {

        switch (button) {
            case CINEMA:
                if (cinemaButton == false) {
                    //如果没有被用户选中，就选中
                    toBig(cinema);
                    cinemaButton = true;

                    if (movieButton == true) {
                        //如果被选中就不选中
                        toSmall(movie);
                        movieButton = false;
                    }
                    if (mineButton == true) {
                        //如果被选中就不选中
                        toSmall(mine);
                        mineButton = false;
                    }
                }

                break;
            case MOVIE:
                if (movieButton == false) {
                    //如果没有被用户选中，就选中
                    toBig(movie);
                    movieButton = true;

                    if (cinemaButton == true) {
                        //如果被选中就不选中
                        toSmall(cinema);
                        cinemaButton = false;
                    }
                    if (mineButton == true) {
                        //如果被选中就不选中
                        toSmall(mine);
                        mineButton = false;
                    }
                }
                break;

            case MINE:
                if (mineButton == false) {
                    //如果没有被用户选中，就选中
                    toBig(mine);
                    mineButton = true;

                    if (movieButton == true) {
                        //如果被选中就不选中
                        toSmall(movie);
                        movieButton = false;
                    }
                    if (cinemaButton == true) {
                        //如果被选中就不选中
                        toSmall(cinema);
                        cinemaButton = false;
                    }
                }
                break;

        }


    }


    //按钮变大
    public void toBig(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = layoutParams.height / 6 * 7;
        layoutParams.width = layoutParams.width / 6 * 7;
        view.setLayoutParams(layoutParams);

    }

    //按钮变小
    public void toSmall(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = layoutParams.height / 7 * 6;
        layoutParams.width = layoutParams.width / 7 * 6;
        view.setLayoutParams(layoutParams);

    }


}

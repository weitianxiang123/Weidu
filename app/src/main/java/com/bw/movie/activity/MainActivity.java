package com.bw.movie.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.presenter.MainActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivityPresenter> {
    private boolean cinemaButton = false;
    private boolean movieButton = false;
    private boolean mineButton = false;

    private final int CINEMA = 1;
    private final int MOVIE = 2;
    private final int MINE = 3;

    @BindView(R.id.cinema)
    ImageView cinema;
    @BindView(R.id.movie)
    ImageView movie;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.contentView)
    FrameLayout contentView;


    @Override
    public Class<MainActivityPresenter> getClassDelegate() {
        return MainActivityPresenter.class;
    }


    @Override
    public void initView() {
        super.initView();
        delegate.initView(cinema, movie, mine, contentView);

        delegate.toMovie();
        selectButton(MOVIE);
    }

    @OnClick({R.id.cinema, R.id.movie, R.id.mine})
    public void click(View view) {
        // 点击图片  放大 切换图片  跳转到相应的fragment
        switch (view.getId()) {
            default:
                break;
            case R.id.cinema:
                // 影院页面
                delegate.toCinema();
                selectButton(CINEMA);
                break;
            case R.id.movie:
                // 电影页面


                delegate.toMovie();
                selectButton(MOVIE);

                break;
            case R.id.mine:
                // 我的页面
                delegate.toMine();

                selectButton(MINE);
                break;
        }
    }


    //设置选中的Button
    void selectButton(int button) {

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



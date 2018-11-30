package com.bw.movie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseFragment;
import com.bw.movie.presenter.MineFragmentPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：owner on 2018/11/28 09:41
 */
public class MineFragment extends BaseFragment<MineFragmentPresenter> {

    @BindView(R.id.mine_messiage)
    RelativeLayout mMessiageRelativeLayout;
    @BindView(R.id.mine_attention)
    RelativeLayout mAttentionRelativeLayout;
    @BindView(R.id.mine_rccord)
    RelativeLayout mRccordRelativeLayout;
    @BindView(R.id.mine_feedback)
    RelativeLayout mFeedbackRelativeLayout;
    @BindView(R.id.mine_version)
    RelativeLayout mVersionRelativeLayout;
    @BindView(R.id.mine_remind)
    ImageView mRemindRelativeLayout;
    @BindView(R.id.logo_layout)
    LinearLayout logoLinearLayout;
    @BindView(R.id.head_img)
    SimpleDraweeView headSimpleDraweeView;
    @BindView(R.id.login_state)
    TextView stateTextView;

    @Override
    public Class<MineFragmentPresenter> getClassDelegate() {
        return MineFragmentPresenter.class;
    }

    @OnClick({R.id.mine_messiage, R.id.mine_attention, R.id.mine_rccord, R.id.mine_feedback, R.id.mine_version, R.id.mine_remind, R.id.logo_layout})
    public void click(View view) {

        switch (view.getId()) {
            default:
                break;
            //我的信息
            case R.id.mine_messiage:

                delegate.tomessiage();
                break;
            //我的关注
            case R.id.mine_attention:
                delegate.toattention();
                break;
            //购票信息
            case R.id.mine_rccord:
                delegate.torccord();
                break;
            //意见反馈
            case R.id.mine_feedback:
                delegate.tofeedback();
                break;
            //最新版本
            case R.id.mine_version:
                delegate.toversion();
                break;
            //系统消息
            case R.id.mine_remind:
                delegate.toremind();
                break;
            case R.id.logo_layout:
                delegate.logoLayout();
                break;

        }

    }

    @Override
    public void initView() {
        super.initView();
    delegate.initView(stateTextView,headSimpleDraweeView);
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.mineResume();
    }
}

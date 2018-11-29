package com.bw.movie.cview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bw.movie.R;

/**
 * 作者：owner on 2018/11/29 09:11
 */
public class BackView extends RelativeLayout {
    public BackView(Context context) {
        super(context);
        init(context);
    }

    public BackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(final Context context) {
        this.context = context;
        View inflate = View.inflate(context, R.layout.layout_finish, null);
        ImageView mImageView = inflate.findViewById(R.id.back_this);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });
        addView(inflate);
    }
}

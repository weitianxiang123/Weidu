package com.bw.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.base.BaseActivity;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.presenter.UpdateInfoActivityPresenter;
import com.bw.movie.utils.FileSaveUtil;
import com.bw.movie.utils.SelectPicUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bw.movie.utils.SelectPicUtil.getAfterCropBitmap;

public class UpdateInfoActivity extends BaseActivity<UpdateInfoActivityPresenter> {

    @BindView(R.id.update_head)
    SimpleDraweeView mUpdateHead;
    @BindView(R.id.update_layout)
    RelativeLayout mUpdateLayout;
    @BindView(R.id.update_name)
    EditText mUpdateName;
    @BindView(R.id.update_sex)
    EditText mUpdateSex;
    @BindView(R.id.update_data)
    EditText mUpdateData;
    @BindView(R.id.update_mobile)
    EditText mUpdateMobile;
    @BindView(R.id.update_emil)
    EditText mUpdateEmil;

    @BindView(R.id.save_updata_info)
    Button mSaveUpdataInfo;

    @Override
    public Class<UpdateInfoActivityPresenter> getClassDelegate() {
        return UpdateInfoActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.initView(mUpdateHead,mUpdateLayout,mUpdateName,mUpdateSex,mUpdateData,mUpdateMobile,mUpdateEmil);
    }

    @OnClick(R.id.save_updata_info)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.save_updata_info:
                delegate.updateInfo();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegate.head(requestCode, resultCode, data);

        switch (requestCode) {
            case SelectPicUtil.PHOTO_REQUEST_CAMERA:
                File cameraTempFile=SelectPicUtil.getCameraTempFile();
                if (cameraTempFile != null) SelectPicUtil.openCropActivity( this, Uri.fromFile(cameraTempFile));
                break;
            case SelectPicUtil.PHOTO_REQUEST_CUT:
               /* Toast.makeText(this, "走啊", Toast.LENGTH_SHORT).show();*/
                Bitmap bitmap = getAfterCropBitmap(this);
               /* try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                String s = FileSaveUtil.saveBitmap(this, bitmap);

                File filep=new File(s);
                RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"),filep);
                MultipartBody.Part part=MultipartBody.Part.createFormData("image","a15201227200.jpg",file);
                new HttpHelper(this).upHeadImage(HttpUrl.STRING_BTN_HEAD,part).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(UpdateInfoActivity.this, ""+data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String error) {
                        Toast.makeText(UpdateInfoActivity.this, "失败"+error, Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case SelectPicUtil.PHOTO_REQUEST_GALLERY:
                if (data != null)  SelectPicUtil.openCropActivity(this,data.getData());
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();
    }
}

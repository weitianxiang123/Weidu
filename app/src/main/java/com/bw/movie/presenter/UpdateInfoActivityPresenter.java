package com.bw.movie.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.model.LoginBean;
import com.bw.movie.model.RootMessage;
import com.bw.movie.model.UpdateInfoBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.DateFormatForYou;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.FileSaveUtil;
import com.bw.movie.utils.SelectPicUtil;
import com.bw.movie.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import butterknife.internal.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：owner on 2018/12/3 14:36
 */
public class UpdateInfoActivityPresenter extends AppDelegate {
    private SimpleDraweeView mUpdateHead;
    private RelativeLayout mUpdateLayout;
    private EditText mUpdateName;
    private EditText mUpdateSex;
    private EditText mUpdateData;
    private EditText mUpdateMobile;
    private EditText mUpdateEmil;
    private Context context;
    private int userId;
    private String sessionId;
    private RootMessage rootMessage;
    private boolean isLogin;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private Button popup_quxiao;
    private Button popup_xiangji;
    private Button popup_xiangce;
    private PopupWindow popupWindow;
    private static String path;//sd路径
    private View inflate;
    private Bitmap head;//头像Bitmap

    @Override
    public int getLayout() {
        return R.layout.activity_update_info;
    }

    @Override
    public void initContext(Context context) {
        this.context = context;
    }

    public void initView(SimpleDraweeView mUpdateHead, RelativeLayout mUpdateLayout, EditText mUpdateName, EditText mUpdateSex, EditText mUpdateData, EditText mUpdateMobile, EditText mUpdateEmil) {
        this.mUpdateHead = mUpdateHead;
        this.mUpdateLayout = mUpdateLayout;
        this.mUpdateName = mUpdateName;
        this.mUpdateSex = mUpdateSex;
        this.mUpdateData = mUpdateData;
        this.mUpdateMobile = mUpdateMobile;
        this.mUpdateEmil = mUpdateEmil;
    }

    @Override
    public void initData() {
        super.initData();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myhead/";//sd路径
        inflate = View.inflate(context, R.layout.popupwindow, null);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, 400, true);
        // new DateFormatForYou().longToString(lastLoginTime, "yyyy-MM-dd")

        RootMessage.ResultBean rootMessage = ShareUtil.getRootMessage(context).getResult();
        popup_quxiao = inflate.findViewById(R.id.popup_quxiao);
        popup_xiangce = inflate.findViewById(R.id.popup_xiangce);
        popup_xiangji = inflate.findViewById(R.id.popup_xiangji);
        userId = rootMessage.getUserId();
        sessionId = rootMessage.getSessionId();
        RootMessage.ResultBean.UserInfoBean userInfo = rootMessage.getUserInfo();
        initSetText(userInfo);
        //点击弹出框
        mUpdateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
            }
        });
        //相机
        popup_xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置调用系统摄像头的意图(隐式意图)
          /*      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //设置照片的输出路径和文件名
                //设置图片的名称
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.png")));
                //开启摄像头
                Activity a = (Activity) context;
                a.startActivityForResult(intent, 1);*/
                SelectPicUtil.openCamera((Activity) context);
                popupWindow.dismiss();
            }
        });
        //相册
        popup_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  // 设置调用系统相册的意图(隐式意图)
                Intent intent = new Intent();
                //设置值活动//android.intent.action.PICK
                intent.setAction(Intent.ACTION_PICK);
                //设置类型和数据
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                // 开启系统的相册
                Activity b = (Activity) context;
                b.startActivityForResult(intent, 2);*/

                SelectPicUtil.openGallery((Activity) context);
                popupWindow.dismiss();

            }
        });
        //取消
        popup_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 当第刚进入页面给text赋值
     *
     * @param userInfo
     */
    private void initSetText(RootMessage.ResultBean.UserInfoBean userInfo) {
        mUpdateHead.setImageURI(userInfo.getHeadPic());
        mUpdateName.setText(userInfo.getNickName());

        if (userInfo.getSex() == 1) {
            mUpdateSex.setText("男");
        } else {
            mUpdateSex.setText("女");
        }
        long lastLoginTime = userInfo.getLastLoginTime();
        try {
            mUpdateData.setText(new DateFormatForYou().longToString(lastLoginTime, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mUpdateMobile.setText(userInfo.getPhone());
    }

    public void updateInfo() {
        initUpdateText(userId, sessionId);
    }

    /**
     * 修改数据
     * 更新数据
     *
     * @param userId
     * @param sessionId
     */
    private void initUpdateText(int userId, String sessionId) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", mUpdateName.getText().toString());
        String text = mUpdateSex.getText().toString();
        if ("男".equals(text)) {
            map.put("sex", "1");
        } else if ("女".equals(text)) {
            map.put("sex", "2");
        } else {
            Toast.makeText(context, "请输入正确的性别", Toast.LENGTH_SHORT).show();
        }
        /*
        这里的put"email"因为接口问题不得不设置成死数据
         */
        map.put("email", "123@qq.com");
        //修改信息的网络请求
        new HttpHelper(context).minePost(HttpUrl.STRING_UPDATA_USER, map, headMap).result(new HttpListener() {
            @Override
            public void success(String data) {
                UpdateInfoBean updateInfoBean = new Gson().fromJson(data, UpdateInfoBean.class);
                if ("1001".equals(updateInfoBean.getStatus())) {
                    Toast.makeText(context, updateInfoBean.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    String phone = pref.getString("account", "");
                    ;
                    String possword = pref.getString("password", "");
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phone);
                    final String encrypt = EncryptUtil.encrypt(possword);
                    map.put("pwd", encrypt);
                    /**
                     * 在修改完数据之后重新请求下, 刷新shard里面的用户数据
                     *
                     */
                    new HttpHelper(context).lrPost(HttpUrl.STRING_LOGIN, map).result(new HttpListener() {
                        @Override
                        public void success(String data) {

                            LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                            String status = loginBean.getStatus();
                            if ("0000".equals(status)) {
                                ShareUtil.saveLogin(data, context);
                                isLogin();
                                ((Activity) context).finish();
                            } else {
                                Toast.makeText(context, "" + loginBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void fail(String error) {
                            Toast.makeText(context, "登录失败" + error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void fail(String error) {
            }
        });
    }

    @Override
    public void rootMessage(boolean isLogin, RootMessage rootMessage) {
        super.rootMessage(isLogin, rootMessage);
        this.isLogin = isLogin;

        this.rootMessage = rootMessage;
    }


    public void head(int requestCode, int resultCode, final Intent data) {

        /*if (!(resultCode == 200))
            return;*/












     /*   Log.i("home", "onActivityResult: " + requestCode + ":" + resultCode);
        Log.i("home", "onActivityResult: " + requestCode + ":" + resultCode);
            switch (requestCode) {
                //相机
                case 1:

                    File temp = new File(path
                            + "/head.png");
                    //裁剪图片
                    startPhotoZoom(Uri.fromFile(temp));
                    break;

                //相册
                case 2:

                    //裁剪图片
                    startPhotoZoom(data.getData());
                    break;

                //剪裁
                case 3:
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    head = extras.getParcelable("data");
                    if (head != null) {

                        mUpdateHead.setImageBitmap(head);
                        String fileName = path +"head.png";//图片名字
                        setPicToView(head);//保存在SD卡中
                        File file1 = new File(fileName);
                        uploadImage(fileName);
                    }
                    break;
            }*/

    }

    private void uploadImage(String fileName) {
        MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(path + "head.png");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(mediaType)
                .addFormDataPart("image", "head.png",
                        RequestBody.create(MediaType.parse("image/png"), file));


        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://172.17.8.100/movieApi/user/v1/verify/uploadHeadPic")
                .post(requestBody)
                .addHeader("sessionId", rootMessage.getResult().getSessionId() + "")
                .addHeader("userId", "" + rootMessage.getResult().getUserId())
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Activity g = (Activity) context;
                g.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", e.getMessage());
                        Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Activity d = (Activity) context;
                d.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        Activity c = (Activity) context;
        c.startActivityForResult(intent, 3);

    }


    public void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.png";//图片名字

        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {

    }
}

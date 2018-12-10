package com.bw.movie.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.activity.MessiageActivity;
import com.bw.movie.application.App;
import com.bw.movie.model.LoginBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.ShareUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
		private static final int RETURN_MSG_TYPE_LOGIN = 1;
		private static final int RETURN_MSG_TYPE_SHARE = 2;

		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			//如果没回调onResp，八成是这句没有写
			App.mWxApi.handleIntent(getIntent(), this);
		}

		// 微信发送请求到第三方应用时，会回调到该方法
		@Override
		public void onReq(BaseReq req) {
		}

		// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
		//app发送消息给微信，处理返回消息的回调
		@Override
		public void onResp(BaseResp resp) {
			Log.d("WXEntryActivity", "错误码 : " + resp.errCode + "");
			switch (resp.errCode) {

				case BaseResp.ErrCode.ERR_AUTH_DENIED:
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					if (RETURN_MSG_TYPE_SHARE == resp.getType()) {

						Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
					}
					else {

						Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
					}
					break;
				case BaseResp.ErrCode.ERR_OK:
					switch (resp.getType()) {
						case RETURN_MSG_TYPE_LOGIN:
							//拿到了微信返回的code,立马再去请求access_token
							String code = ((SendAuth.Resp) resp).code;
							//就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
							Map<String, String> map = new HashMap<>();
							map.put("code",code);
							new HttpHelper(this).lrPost(HttpUrl.STRING_WECHAT_LOGIN,map).result(new HttpListener() {
								@Override
								public void success(String data) {
									LoginBean loginBean = new Gson().fromJson(data,LoginBean.class);
									String status = loginBean.getStatus();
									if ("0000".equals(status)){
										ShareUtil.saveLogin(data,WXEntryActivity.this);
										startActivity(new Intent(WXEntryActivity.this, MessiageActivity.class));
										finish();
									}else {
										Toast.makeText(WXEntryActivity.this, ""+loginBean.getMessage(), Toast.LENGTH_SHORT).show();
									}
								}

								@Override
								public void fail(String error) {

								}
							});
							break;

						case RETURN_MSG_TYPE_SHARE:
							Toast.makeText(this, "微信分享成功", Toast.LENGTH_SHORT).show();
							finish();
							break;
					}
					break;
			}
			//支付成功
			if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("支付结果");
				builder.setMessage(resp.errCode + "");
				builder.show();
			}



		}
	}

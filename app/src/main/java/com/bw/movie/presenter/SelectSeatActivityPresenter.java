package com.bw.movie.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.cview.SeatTable;
import com.bw.movie.model.Order;
import com.bw.movie.model.RootMessage;
import com.bw.movie.model.WX;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.net.HttpUrl;
import com.bw.movie.utils.MD5;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class SelectSeatActivityPresenter extends AppDelegate  implements View.OnClickListener{
	private Context context;
	private SeatTable seatTable;
	int ticketNum=0;
	private boolean isLogin;
	private RootMessage rootMessage;
	private int scheduleId;
	private double price;
	private TextView textPrice;
	private PopupWindow popupWindow;
	private View layoutFather;
	private RadioGroup radioGroup;
	private TextView textPay;
	private Order order;
	private IWXAPI api;
	private int payType;

	@Override
	public int getLayout() {
		return R.layout.activity_select_seat;
	}

	@Override
	public void initContext(Context context) {
		super.initContext(context);
	    this.context=context;

	}

	public void initView(SeatTable seatTable, int scheduleId, double price, TextView textPrice) {
		this.seatTable=seatTable;
		this.scheduleId=scheduleId;
		this.price=1500;
		this.textPrice=textPrice;
	}

	@Override
	public void initData() {
		super.initData();
	 isLogin();

		View fatherView = View.inflate(context, R.layout.pop_movie_pay, null);
		fatherView.findViewById(R.id.imageCancel).setOnClickListener(this);
		fatherView.findViewById(R.id.viewNull).setOnClickListener(this);

		textPay = fatherView.findViewById(R.id.textPay);
		textPay.setOnClickListener(this);
		fatherView.findViewById(R.id.layoutWX).setOnClickListener(this);
		fatherView.findViewById(R.id.layoutZFB).setOnClickListener(this);
		radioGroup = fatherView.findViewById(R.id.radioPay);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        	@Override
	     public void onCheckedChanged(RadioGroup group, int checkedId) {

     		switch (checkedId)
			{
				case R.id.radioWX:
					textPay.setText("微信支付:"+price*ticketNum+"软妹币");
					break;
				case R.id.radioZFB:
					textPay.setText("支付宝支付:"+price*ticketNum+"软妹币");
					break;
			}

     	}
       });
		popupWindow = new PopupWindow(fatherView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		layoutFather =View.inflate(context, R.layout.activity_select_seat,null);


		api = WXAPIFactory.createWXAPI(context, "wxb3852e6a6b7d9516");

	}

	//票数++
	public void add() {
		ticketNum++;
		textPrice.setText(price*ticketNum+"");
	}

	//票数--
	public void subtraction() {
		if (ticketNum!=0)
      ticketNum--;
		textPrice.setText(price*ticketNum+"");
	}

	//取消
	public void cancel() {


	}

	public void conform() {
		if (!isLogin)
		{
			//跳转到登录
			context.startActivity(new Intent(context, LoginActivity.class));
			return;
		}

		if (ticketNum==0)
		{
			Toast.makeText(context, "没有选择座位", Toast.LENGTH_SHORT).show();
		}
		Map<String,String> fMap=new HashMap<>();
		fMap.put("scheduleId",scheduleId+"");
		fMap.put("amount",ticketNum+"");
		fMap.put("sign", MD5.toMD5(""+rootMessage.getResult().getUserId()+scheduleId+ticketNum+"movie"));//使用MD5加密
		//购票网络请求
		new HttpHelper(context).lrHead(HttpUrl.STRING_ORDERS_MOVIE,fMap,null).result(new HttpListener() {
			@Override
			public void success(String data) {
				order = new Gson().fromJson(data, Order.class);
				//在这里弹出支付
				showPay();


			}
			@Override
			public void fail(String error) {

			}
		});


	}

	public  void showPay(){
    popupWindow.showAtLocation(layoutFather, Gravity.BOTTOM,0,0);
		radioGroup.check(R.id.radioWX);
	}

	@Override
	public void rootMessage(boolean isLogin, RootMessage rootMessage) {
		super.rootMessage(isLogin, rootMessage);

	this.isLogin=isLogin;
	this.rootMessage=rootMessage;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId())
		{
			case R.id.imageCancel:
				cancelPop();
				break;
			case R.id.viewNull:
				cancelPop();
				break;
			case R.id.radioPay:
				Toast.makeText(context, "可以", Toast.LENGTH_SHORT).show();

				break;

			case R.id.layoutWX:
				radioGroup.check(R.id.radioWX);
				break;
			case R.id.layoutZFB:
				radioGroup.check(R.id.radioZFB);
				break;
			case R.id.textPay:
				toPay();
				break;
		}
	}

	private void toPay() {

		payType = 1;
		switch (radioGroup.getCheckedRadioButtonId())
		{
			case R.id.radioWX:

              payType =1;
				break;
			case R.id.radioZFB:

            payType =2;
				break;
		}
		Map<String,String> fMap=new HashMap<>();
		fMap.put("payType",""+ payType);
		fMap.put("orderId",order.getOrderId()+"");
		new HttpHelper(context).lrHead(HttpUrl.PAY,fMap,null).result(new HttpListener() {
			@Override
			public void success(String data) {
				if (payType ==1)
				{
					WX wx = new Gson().fromJson(data, WX.class);
					//微信支
					payForWX(wx);
				}

			}

			@Override
			public void fail(String error) {
				Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void payForWX(WX wx){

		PayReq request = new PayReq();
		request.appId = wx.getAppId();
		request.partnerId = wx.getPartnerId();
		request.prepayId= wx.getPrepayId();
		request.packageValue = wx.getPackageValue();
		request.nonceStr= wx.getNonceStr();
		request.timeStamp= wx.getTimeStamp();
		request.sign= wx.getSign();
         api.sendReq(request);


	}

	public boolean cancelPop() {
		if (popupWindow.isShowing())
		{
			popupWindow.dismiss();
			return true;
		}
		return false;
	}

}

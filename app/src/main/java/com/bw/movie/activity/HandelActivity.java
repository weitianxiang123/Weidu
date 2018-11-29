package com.bw.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;

public class HandelActivity extends AppCompatActivity {

    private TextView text_han_name;
    private int text_time=3;
    private Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                if (text_time>1){
                    text_time--;
                    text_han_name.setText(text_time+"s");
                    handle.sendEmptyMessageDelayed(0,2000);
                }else{
                    Intent intent=new Intent(HandelActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handel);
        text_han_name = findViewById(R.id.text_han_name);

        handle.sendEmptyMessageDelayed(0,2000);
    }
}

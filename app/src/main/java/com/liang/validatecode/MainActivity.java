package com.liang.validatecode;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * 短信验证码自动监听填写
 */
public class MainActivity extends AppCompatActivity {

    public static final int MSG_RECEIVED_CODE = 1;

    private SmsObserver smsObserver;

    /**
     * 使用Handler在非UI中更新UI(是一个非常常用的方法)
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECEIVED_CODE){
                String code = (String) msg.obj;
                //更新UI
                mValidateCode.setText(code);
            }

        }
    };
    private EditText mValidateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mValidateCode = (EditText) findViewById(R.id.et_validate_code);

        smsObserver = new SmsObserver(MainActivity.this, mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, smsObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(smsObserver);
    }
}

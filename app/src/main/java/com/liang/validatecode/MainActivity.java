package com.liang.validatecode;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SmsObserver smsObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsObserver = new SmsObserver(MainActivity.this,null);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,smsObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(smsObserver);
    }
}

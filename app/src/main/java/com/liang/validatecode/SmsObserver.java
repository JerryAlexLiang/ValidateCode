package com.liang.validatecode;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by dell on 2017/2/5.
 */
public class SmsObserver extends ContentObserver {

    private static final String TAG = "DEBUG";
    private Context context;
    private Handler handler;

    public SmsObserver(Context mContext,Handler handler) {
        super(handler);
        this.context = mContext;
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        Log.e(TAG, "SMS has changed!");
        Log.e(TAG, "onChange: " + uri.toString());

        if (uri.toString().equals("content://sms/raw")){
            return;
        }
    }
}

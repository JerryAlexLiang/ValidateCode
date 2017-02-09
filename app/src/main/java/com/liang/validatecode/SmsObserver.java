package com.liang.validatecode;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/2/5.
 */
public class SmsObserver extends ContentObserver {

    private static final String TAG = "DEBUG";
    private Context context;
    private Handler handler;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        Log.e(TAG, "SMS has changed!");
        Log.e(TAG, "onChange: " + uri.toString());

        String code = "";

        if (uri.toString().equals("content://sms/raw")) {
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));

                Log.e(TAG, "发件人为： " + address + " " + "短信内容为：" + body);

                //正则表达式---提取短信中连续6个数字的内容
                Pattern pattern = Pattern.compile("(\\d{4})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()) {
                    code = matcher.group(0);

                    Log.e(TAG, "code is : " + code);

                    //向Handler发送消息
                    handler.obtainMessage(AutoValidateCodeActivity.MSG_RECEIVED_CODE,code).sendToTarget();
                }
            }
            cursor.close();
        }
    }
}

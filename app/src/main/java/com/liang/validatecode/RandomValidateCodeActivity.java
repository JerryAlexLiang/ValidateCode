package com.liang.validatecode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RandomValidateCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RandomValidateCodeActivity.class.getName();
    private EditText et_phoneCode;
    private Button but_toSetCode;
    private ImageView iv_showCode;
    //产生的验证码
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_validate_code);
        //初始化视图
        et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes);
        but_toSetCode = (Button) findViewById(R.id.but_forgetpass_toSetCodes);
        iv_showCode = (ImageView) findViewById(R.id.iv_showCode);

        //将验证码用图片的形式显示出来
        changeCode();

        //验证码的点击监听事件
        iv_showCode.setOnClickListener(this);
        //提交验证码的Button的点击监听事件
        but_toSetCode.setOnClickListener(this);
    }

    /**
     * 将验证码用图片的形式显示出来
     */
    private void changeCode() {
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
//        realCode = Code.getInstance().getCode().toLowerCase();//转为小写
        realCode = Code.getInstance().getCode();//区分大小写
    }

    /**
     * 点击监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_showCode:
                //验证码的点击监听事件
                //随机产生验证码
                changeCode();
                Log.i(TAG, "realCode ========> " + realCode);
                break;

            case R.id.but_forgetpass_toSetCodes:
                if (TextUtils.isEmpty(et_phoneCode.getText())) {
                    Toast.makeText(RandomValidateCodeActivity.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
//                String phoneCode = et_phoneCode.getText().toString().toLowerCase();//转为小写
                String phoneCode = et_phoneCode.getText().toString();//区分大小写
                String msg = "生成的验证码： " + realCode + "输入的验证码：" + phoneCode;
                Toast.makeText(RandomValidateCodeActivity.this, msg, Toast.LENGTH_SHORT).show();

                //逻辑判断
                if (phoneCode.equals(realCode)) {
                    Toast.makeText(RandomValidateCodeActivity.this, "验证码正确", Toast.LENGTH_SHORT).show();
                    createDialog();
                } else {
                    Toast.makeText(RandomValidateCodeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    //随机产生验证码
                    changeCode();
                    et_phoneCode.setText("");
                }
                break;
        }
    }

    /**
     * 提示框
     */
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RandomValidateCodeActivity.this)
                .setIcon(R.drawable.guide_home_on)
                .setTitle("提示")
                .setMessage("验证码正确，是否登录？")
                .setCancelable(false)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent intent = new Intent(RandomValidateCodeActivity.this, LoginInActivity.class);
                        startActivity(intent);
                        //验证码更改，EditText清空
                        changeCode();
                        et_phoneCode.setText("");
                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(RandomValidateCodeActivity.this, "你取消了登录！", Toast.LENGTH_SHORT).show();
                        //验证码更改，EditText清空
                        changeCode();
                        et_phoneCode.setText("");
                    }
                });

        builder.create().show();

    }
}

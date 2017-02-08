package com.liang.validatecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 栏目目录
 */
public class TitleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //初始化数据源
    private String[] data = {"自动填写短信验证码","随机产生验证码"};
    private ListView mTitleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        //初始化视图
        mTitleListView = (ListView) findViewById(R.id.lv);
        //初始化适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TitleActivity.this,android.R.layout.simple_list_item_1,data);
        //绑定适配器
        mTitleListView.setAdapter(adapter);
        //初始化点击监听事件
        mTitleListView.setOnItemClickListener(this);
    }

    /**
     * ListView的点击监听事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(TitleActivity.this,AutoValidateCodeActivity.class);
                break;
            case 1:
                intent.setClass(TitleActivity.this,RandomValidateCodeActivity.class);
                break;

        }
        startActivity(intent);
    }
}

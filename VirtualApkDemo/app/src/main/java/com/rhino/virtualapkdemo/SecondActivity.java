package com.rhino.virtualapkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * @author LuoLin
 * @since Create on 2018/5/30.
 **/
public class SecondActivity extends AppCompatActivity {

    private TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
        mTvText = findViewById(R.id.text);

        mTvText.setText("这里是宿主中activity，" + mTvText.getText().toString());
        mTvText.setTextColor(getResources().getColor(R.color.colorAccent));

        int id = getResources().getIdentifier("activity_plugin_main", "layout", getPackageName());
        Log.d("RHINO", "host -> activity_plugin_main ID: " + id);
    }

}

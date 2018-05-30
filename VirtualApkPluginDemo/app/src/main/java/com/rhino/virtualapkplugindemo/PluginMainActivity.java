package com.rhino.virtualapkplugindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PluginMainActivity extends AppCompatActivity {

    private TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
        mTvText = findViewById(R.id.text);

        mTvText.setText("这里是插件中activity，" + mTvText.getText().toString());
        mTvText.setTextColor(getResources().getColor(R.color.colorAccent));

        int id = getResources().getIdentifier("activity_plugin_main", "layout", getPackageName());
        Log.d("RHINO", "plugin -> activity_plugin_main ID: " + id);

    }
}

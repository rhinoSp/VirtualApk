package com.rhino.virtualapkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;

import java.io.File;

/**
 * @author LuoLin
 * @since Create on 2018/5/24.
 */
public class MainActivity extends AppCompatActivity {

    /** 插件文件路径 **/
    private static final String PLUGIN_FILE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "plugin_demo.apk";
    /** 插件apk包名 **/
    private static final String PLUGIN_PACKAGE_NAME = "com.rhino.virtualapkplugindemo";
    /** 插件apk入口activity **/
    private static final String PLUGIN_LAUNCHER_ACTIVITY = "com.rhino.virtualapkplugindemo.PluginMainActivity";

    /** 请求权限请求码 **/
    public static final int REQUEST_PERMISSION_CODE = 111;
    /** 权限 **/
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (lacksPermissions(PERMISSIONS)) {
            // 开始请求权限
            requestPermissions(PERMISSIONS);
        } else {
            loadPlugin();
        }

        findViewById(R.id.show_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.run_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPluginActivity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            loadPlugin();
        }
    }

    /** 判断是否缺少权限 **/
    private boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }

    /** 请求权限 **/
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
    }

    /** 加载插件 **/
    private void loadPlugin() {
        PluginManager pluginManager = PluginManager.getInstance(this);
        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载,达到热修复)
        File pluginApk = new File(PLUGIN_FILE_PATH);
        if (pluginApk.exists()) {
            try {
                pluginManager.loadPlugin(pluginApk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** 运行插件中activity **/
    private void showPluginActivity() {
        if (PluginManager.getInstance(this).getLoadedPlugin(PLUGIN_PACKAGE_NAME) == null) {
            Toast.makeText(getApplicationContext(),"插件未加载,请尝试重启APP", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(PLUGIN_PACKAGE_NAME, PLUGIN_LAUNCHER_ACTIVITY);
        startActivity(intent);
    }
}

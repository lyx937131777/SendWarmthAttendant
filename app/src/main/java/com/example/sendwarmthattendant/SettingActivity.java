package com.example.sendwarmthattendant;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.SettingPresenter;
import com.example.sendwarmthattendant.service.DownloadService;
import com.example.sendwarmthattendant.util.HttpUtil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingActivity extends AppCompatActivity
{
    private TextView versionText;
    private Button updateButton;

    private SharedPreferences pref;
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection()
    {

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }
    };

    private SettingPresenter settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        settingPresenter = myComponent.settingPresenter();

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent); // 启动服务
        bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务

        versionText = findViewById(R.id.version);
        updateButton = findViewById(R.id.update);

        PackageManager manager = getPackageManager();
        String version = "未知";
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionText.setText(version);
        final String finalVersion = version;
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return;
                }
                String latestVersion = pref.getString("latestVersion","");
                if(latestVersion.equals(finalVersion) || latestVersion.equals("")){
                    Toast.makeText(SettingActivity.this,"目前已是最新版本",Toast.LENGTH_LONG).show();
                }else{
                    if (downloadBinder == null)
                    {
                        Toast.makeText(SettingActivity.this,"downloadBinder为空",Toast.LENGTH_LONG).show();
                        return;
                    }
                    downloadBinder.startDownload(HttpUtil.getResourceURL(pref.getString("latestVersionDownloadUrl","")));
                }
            }
        });

        settingPresenter.getLatestVersion(version);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "你拒绝了下载权限，将无法进行系统更新", Toast.LENGTH_SHORT).show();
                }else {
                    updateButton.performClick();
                }
                break;
            default:
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(connection);
    }
}
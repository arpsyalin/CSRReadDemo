package priv.lyl.csvread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import priv.lyl.csvread.constant.Constant;
import priv.lyl.csvread.model.CommonModel;
import priv.lyl.csvread.model.Order;
import priv.lyl.csvread.utils.EntityUtils;
import priv.lyl.csvread.utils.ServiceUtils;
import priv.lyl.csvread.service.ReadFileService;
import priv.lyl.readmodel.constant.PreferencesConstant;
import priv.lyl.readmodel.model.FileResource;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection, SharedPreferences.OnSharedPreferenceChangeListener {
    String mPath = null;
    ReadFileService mReadFileService;
    ListView mListView;
    List<String> mData;
    ArrayAdapter<String> mAdapter;
    BroadcastReceiver mDataBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceUtils.bindService(this, this);
        requestPermission();
        mData = new ArrayList<>();
        mListView = findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(mAdapter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PreferencesConstant.READFILE);
        mDataBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(PreferencesConstant.READFILE)) {
                    String url = intent.getStringExtra(PreferencesConstant.URL);
                    String data = intent.getStringExtra(Intent.EXTRA_TEXT);
                    Gson gson = new Gson();
                    CommonModel model = gson.fromJson(data, CommonModel.class);
                    final List<Order> orders = EntityUtils.castEntity(model, Order.class, new Order());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mData.clear();
                            for (Order o : orders)
                                mData.add(o.toString());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        };
        registerReceiver(mDataBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getSharedPreferences(PreferencesConstant.READFILE, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        getSharedPreferences(PreferencesConstant.READFILE, Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServiceUtils.unbindService(this, this);
        unregisterReceiver(mDataBroadcastReceiver);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mReadFileService = ((ServiceUtils.ServiceBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mReadFileService = null;
    }

    /*
            选择文件
         */
    public void selectFile(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, Constant.SELECTFILE);
    }

    /**
     * 开始
     *
     * @param view
     */
    public void start(View view) {
        if (mPath != null) {
            FileResource fileResource = new FileResource();
            fileResource.setUri(mPath);
            fileResource.setIndex(FileResource.END);
            if (mReadFileService != null) {
                mReadFileService.put(fileResource);
            }
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constant.REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constant.REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.SELECTFILE:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        // 用户未选择任何文件，直接返回
                        return;
                    }
                    Uri uri = data.getData(); // 获取用户选择文件的URI
                    // 通过ContentProvider查询文件路径
                    ContentResolver resolver = this.getContentResolver();
                    Cursor cursor = resolver.query(uri, null, null, null, null);
                    if (cursor == null) {
                        // 未查询到，说明为普通文件，可直接通过URI获取文件路径
                        mPath = uri.getPath();
                        return;
                    }
                    if (cursor.moveToFirst()) {
                        // 多媒体文件，从数据库中获取文件的真实路径
                        mPath = cursor.getString(cursor.getColumnIndex("_data"));
                    }
                    cursor.close();
                }
                break;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, final String key) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences(PreferencesConstant.READFILE, Context.MODE_PRIVATE);
                String data = sharedPreferences1.getString(mPath, "");
             }
        });
    }
}

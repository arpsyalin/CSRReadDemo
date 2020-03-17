package priv.lyl.readmodel.distribute;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.JsonReader;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import priv.lyl.readmodel.application.ReadApplication;
import priv.lyl.readmodel.constant.PreferencesConstant;
import priv.lyl.readmodel.interf.IResultDistribute;
import priv.lyl.readmodel.model.FileResource;

public class ResultDistributeImpl implements IResultDistribute {

    @Override
    public void distribute(List<List<String>> arr, FileResource mFileResource) {
 //        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(ReadApplication.getInstance());
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        Intent intent = new Intent(PreferencesConstant.READFILE);      //创建发送广播的Action
        intent.putExtra(Intent.EXTRA_TEXT, json);
        intent.putExtra(PreferencesConstant.URL, mFileResource.getUri());
//        localBroadcastManager.sendBroadcast(intent);
        ReadApplication.getInstance().sendBroadcast(intent);
//        SharedPreferences.Editor editor = ReadApplication.getInstance().getSharedPreferences(PreferencesConstant.READFILE,
//                Context.MODE_PRIVATE).edit();
//        editor.putString(mFileResource.getUri(), json);
//        editor.commit();
        //或者存入数据库
    }

    @Override
    public void distributeObject(List<Object[]> arr, FileResource mFileResource) {

    }
}

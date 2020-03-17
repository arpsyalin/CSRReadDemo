package priv.lyl.csvread.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import androidx.annotation.Nullable;
import priv.lyl.csvread.utils.ServiceUtils;
import priv.lyl.readmodel.model.FileResource;
import priv.lyl.readmodel.mutiltread.FileReadManager;
import priv.lyl.readmodel.mutiltread.FileReadManagerThread;
import priv.lyl.readmodel.mutiltread.ReadFileTask;

public class ReadFileService extends Service implements IReadFileService {
    FileReadManager mFileReadManager;
    FileReadManagerThread mFileReadManagerThread;
    ServiceUtils.ServiceBinder mBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new ServiceUtils.ServiceBinder(this);
        mFileReadManager = FileReadManager.getInstance();
        mFileReadManagerThread = new FileReadManagerThread();
        new Thread(mFileReadManagerThread).start();
    }


    @Override
    public void put(FileResource item) {
        if (mFileReadManager != null)
            mFileReadManager.addReadFileTask(new ReadFileTask(item));
    }

    @Override
    public void putAll(List<FileResource> items) {
        if (mFileReadManager != null) {
            for (FileResource rft : items) {
                put(rft);
            }
        }

    }

    @Override
    public void remove(FileResource fileResource) {
        if (mFileReadManager != null) {
            mFileReadManager.removeReadFileTask(new ReadFileTask(fileResource));
        }
    }

    @Override
    public void removeAll() {
        if (mFileReadManager != null) {
            mFileReadManager.removeAll();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFileReadManagerThread.setStop(true);
    }

    public ReadFileService() {
        super();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }
}

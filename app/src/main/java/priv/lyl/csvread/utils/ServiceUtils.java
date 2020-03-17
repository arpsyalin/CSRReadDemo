package priv.lyl.csvread.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;

import java.lang.ref.WeakReference;

import priv.lyl.csvread.service.ReadFileService;

public class ServiceUtils {
    public static Intent getServiceIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("priv.lyl.csvread", "priv.lyl.csvread.service.ReadFileService"));
        return intent;
    }

    public static void bindService(Context context,
                                   ServiceConnection connection) {
        if (context == null || connection == null)
            return;
        final Intent intent = getServiceIntent();
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public static void unbindService(Context context,
                                     ServiceConnection connection) {
        if (context == null || connection == null)
            return;
        context.unbindService(connection);
    }

    public static class ServiceBinder extends Binder {
        private WeakReference<ReadFileService> mService;

        public ServiceBinder(ReadFileService service) {
            mService = new WeakReference<>(service);
        }

        public ReadFileService getService() {
            return mService.get();
        }
    }
}

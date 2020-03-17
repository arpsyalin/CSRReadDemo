package priv.lyl.readmodel.application;

import android.app.Application;

public class ReadApplication extends Application {
    private static ReadApplication application;

    public static ReadApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        application = null;
    }
}

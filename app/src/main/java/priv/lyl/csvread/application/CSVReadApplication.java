package priv.lyl.csvread.application;

import com.squareup.leakcanary.LeakCanary;

import priv.lyl.readmodel.application.ReadApplication;

public class CSVReadApplication extends ReadApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}

package dev.jx.ec03;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class CodemoniumApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}

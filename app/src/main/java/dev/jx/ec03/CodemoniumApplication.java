package dev.jx.ec03;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import dev.jx.ec03.entity.User;

public class CodemoniumApplication extends Application {

    private static Context context;
    private static User user;

    public static Context getAppContext() {
        return context;
    }

    @Nullable
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CodemoniumApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}

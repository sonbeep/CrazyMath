package com.example.crazymath;

import android.app.Application;

public class App extends Application {
    public static App INSTANCE;
    private Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        storage = new Storage();
    }

    public static App getINSTANCE() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }
}

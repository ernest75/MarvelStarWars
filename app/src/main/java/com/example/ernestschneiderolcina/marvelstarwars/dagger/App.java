package com.example.ernestschneiderolcina.marvelstarwars.dagger;

import android.app.Application;



public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkingModule(new NetworkingModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }
}

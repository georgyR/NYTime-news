package com.androidacademy.msk.exerciseproject.di;

import android.content.Context;

import com.androidacademy.msk.exerciseproject.di.component.AboutComponent;
import com.androidacademy.msk.exerciseproject.di.component.AppComponent;
import com.androidacademy.msk.exerciseproject.di.component.DaggerAppComponent;
import com.androidacademy.msk.exerciseproject.di.component.DbAndNetworkComponent;
import com.androidacademy.msk.exerciseproject.di.component.DbComponent;
import com.androidacademy.msk.exerciseproject.di.component.IntroComponent;
import com.androidacademy.msk.exerciseproject.di.component.NewsDetailsComponent;
import com.androidacademy.msk.exerciseproject.di.component.NewsListComponent;
import com.androidacademy.msk.exerciseproject.di.module.AboutModule;
import com.androidacademy.msk.exerciseproject.di.module.AppModule;
import com.androidacademy.msk.exerciseproject.di.module.DbModule;
import com.androidacademy.msk.exerciseproject.di.module.IntroModule;
import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsListModule;


public class Injector {

    private static Injector instance;
    private final AppComponent appComponent;
    private final DbComponent dbComponent;
    private final DbAndNetworkComponent dbAndNetworkComponent;

    private Injector(Context appContext) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext))
                .build();
        dbComponent = appComponent.plusDbComponent(new DbModule());

        dbAndNetworkComponent = dbComponent.plusNetworkComponent(new NetworkModule());
    }

    public static Injector getInstance(Context context) {
        if (instance == null) {
            instance = new Injector(context);
        }
        return  instance;
    }

    public NewsListComponent getNewsListComponent() {
        return dbAndNetworkComponent.plusNewsListComponent(new NewsListModule());
    }

    public AboutComponent getAboutComponent() {
        return appComponent.plusAboutComponent(new AboutModule());
    }

    public IntroComponent getIntroComponent() {
        return appComponent.plusIntroComponent(new IntroModule());
    }

    public NewsDetailsComponent getNewsDetailsComponent(int currentItemId) {
       return dbComponent.plusNewsDetailsComponent(new NewsDetailsModule(currentItemId));
    }

}

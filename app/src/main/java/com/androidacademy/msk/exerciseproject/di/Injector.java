package com.androidacademy.msk.exerciseproject.di;

import android.content.Context;

import com.androidacademy.msk.exerciseproject.di.component.DaggerDbComponent;
import com.androidacademy.msk.exerciseproject.di.component.DbAndNetworkComponent;
import com.androidacademy.msk.exerciseproject.di.component.DbComponent;
import com.androidacademy.msk.exerciseproject.di.component.NewsDetailsComponent;
import com.androidacademy.msk.exerciseproject.di.component.NewsEditorComponent;
import com.androidacademy.msk.exerciseproject.di.component.NewsListComponent;
import com.androidacademy.msk.exerciseproject.di.module.AppModule;
import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsEditorModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsListModule;


public class Injector {

    private static Injector instance;
    private final DbAndNetworkComponent dbAndNetworkComponent;
    private final DbComponent dbComponent;

    private Injector(Context appContext) {
        dbComponent = DaggerDbComponent.builder()
                .appModule(new AppModule(appContext))
                .build();
        dbAndNetworkComponent = dbComponent.plusDbAndNetworkComponent(new NetworkModule());
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

    public NewsDetailsComponent getNewsDetailsComponent(int currentItemId) {
       return dbComponent.plusNewsDetailsComponent(new NewsDetailsModule(currentItemId));
    }

    public NewsEditorComponent getNewsEditorComponent(int currentItemId) {
        return dbComponent.plusNewsEditorComponent(new NewsEditorModule(currentItemId));
    }

}

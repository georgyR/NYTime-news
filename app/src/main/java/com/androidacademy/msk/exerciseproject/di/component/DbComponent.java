package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.AppModule;
import com.androidacademy.msk.exerciseproject.di.module.DbModule;
import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsEditorModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, DbModule.class})
@Singleton
public interface DbComponent {
    NewsDetailsComponent plusNewsDetailsComponent(NewsDetailsModule module);

    NewsEditorComponent plusNewsEditorComponent(NewsEditorModule module);

    DbAndNetworkComponent plusDbAndNetworkComponent(NetworkModule module);
}

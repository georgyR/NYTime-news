package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.AppModule;
import com.androidacademy.msk.exerciseproject.di.module.DbModule;
import com.androidacademy.msk.exerciseproject.di.module.AboutModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    DbComponent plusDbComponent(DbModule module);

    AboutComponent plusAboutComponent(AboutModule module);
}

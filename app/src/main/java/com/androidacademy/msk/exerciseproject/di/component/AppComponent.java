package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.AppModule;
import com.androidacademy.msk.exerciseproject.di.module.DbModule;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;
import com.androidacademy.msk.exerciseproject.screen.intro.IntroActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    DbComponent plusDbComponent(DbModule module);

    void inject(AboutActivity aboutActivity);

    void inject(IntroActivity introActivity);

}

package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.module.NewsListModule;
import com.androidacademy.msk.exerciseproject.di.scope.NetworkScope;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Subcomponent(modules = {NetworkModule.class})
@NetworkScope
public interface DbAndNetworkComponent {
    NewsListComponent plusNewsListComponent(NewsListModule module);
}

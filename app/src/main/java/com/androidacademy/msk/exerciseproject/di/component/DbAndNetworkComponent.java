package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NetworkModule;
import com.androidacademy.msk.exerciseproject.di.scope.NetworkScope;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {NetworkModule.class})
@NetworkScope
public interface DbAndNetworkComponent {

    void inject(NewsListActivity newsListActivity);
}

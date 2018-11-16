package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsListModule;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListFragment;

import dagger.Subcomponent;

@Subcomponent(modules = NewsListModule.class)
@ActivityScope
public interface NewsListComponent {
    void inject(NewsListFragment newsListFragment);
}

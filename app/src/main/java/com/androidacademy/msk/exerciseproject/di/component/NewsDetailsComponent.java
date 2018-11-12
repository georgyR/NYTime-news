package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = NewsDetailsModule.class)
@ActivityScope
public interface NewsDetailsComponent {

    void inject(NewsDetailsActivity newsDetailsActivity);
}

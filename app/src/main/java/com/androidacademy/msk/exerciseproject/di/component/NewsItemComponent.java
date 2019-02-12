package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsItemIdModule;
import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.newsdetails.NewsDetailsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = NewsItemIdModule.class)
@FragmentScope
public interface NewsItemComponent {

    void inject(NewsDetailsFragment newsDetailsFragment);

}

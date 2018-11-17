package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule;
import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = NewsDetailsModule.class)
@FragmentScope
public interface NewsDetailsComponent {

    void inject(NewsDetailsFragment newsDetailsFragment);
}

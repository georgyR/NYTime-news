package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.AboutModule;
import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;

import dagger.Subcomponent;

@Subcomponent(modules = AboutModule.class)
@FragmentScope
public interface AboutComponent {

    void inject(AboutActivity aboutActivity);
}

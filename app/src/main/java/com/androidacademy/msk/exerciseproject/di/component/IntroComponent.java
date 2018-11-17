package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.IntroModule;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.intro.IntroActivity;

import dagger.Subcomponent;

@Subcomponent(modules = IntroModule.class)
@ActivityScope
public interface IntroComponent {

    void inject(IntroActivity introActivity);

}

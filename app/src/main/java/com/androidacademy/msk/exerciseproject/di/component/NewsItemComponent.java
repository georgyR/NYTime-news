package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsItemIdModule;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsActivity;
import com.androidacademy.msk.exerciseproject.screen.news_editor.NewsEditorActivity;

import dagger.Subcomponent;

@Subcomponent(modules = NewsItemIdModule.class)
@ActivityScope
public interface NewsItemComponent {

    void inject(NewsDetailsActivity newsDetailsActivity);

    void inject(NewsEditorActivity newsEditorActivity);
}

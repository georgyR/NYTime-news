package com.androidacademy.msk.exerciseproject.di.component;

import com.androidacademy.msk.exerciseproject.di.module.NewsEditorModule;
import com.androidacademy.msk.exerciseproject.di.scope.PresenterScope;
import com.androidacademy.msk.exerciseproject.screen.news_editor.NewsEditorActivity;

import dagger.Subcomponent;

@Subcomponent(modules = NewsEditorModule.class)
@PresenterScope
public interface NewsEditorComponent {

    void inject(NewsEditorActivity newsEditorActivity);
}

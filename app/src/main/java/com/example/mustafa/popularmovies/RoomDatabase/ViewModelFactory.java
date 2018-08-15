package com.example.mustafa.popularmovies.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private int id;


    public ViewModelFactory(Application application, int id) {
        mApplication = application;
        this.id = id;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieViewModel(mApplication, id);
    }
}
package com.example.mustafa.popularmovies.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mustafa.popularmovies.DataItems.MovieItem;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;

    private LiveData<List<MovieItem>> mAllMovies;

    private LiveData<MovieItem> mMovie;

    public MovieViewModel (@NonNull Application application, int id) {
        super(application);
        moviesRepository = new MoviesRepository(application,id);
        mAllMovies = moviesRepository.getmAllMovies();
        mMovie=moviesRepository.getmMovie();
    }

    public LiveData<List<MovieItem>> getmAllMovies() { return mAllMovies; }

    public LiveData<MovieItem> getmMovie() { return mMovie; }

    public void insert(MovieItem movieItem) { moviesRepository.insert(movieItem); }

    public void delete(MovieItem movieItem){moviesRepository.delete(movieItem);}
}



package com.example.mustafa.popularmovies.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.mustafa.popularmovies.DataItems.MovieItem;

import java.util.List;

public class MoviesRepository {


    private MovieDao mMovieDao;
    private LiveData<List<MovieItem>> mAllMovies;
    private LiveData <MovieItem> mMovie;


    MoviesRepository(Application application,int id) {
        MoviesDatabase db = MoviesDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
        mMovie=mMovieDao.getMovie(id);
    }

    public LiveData<List<MovieItem>>getmAllMovies(){
        return mAllMovies;
    }

    public LiveData<MovieItem>getmMovie(){
        return mMovie;
    }

    public void insert (final MovieItem movieItem) {
        DatabaseExecuter.getsInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insert(movieItem);
            }
        });

    }

    public void delete (final MovieItem movieItem){
        DatabaseExecuter.getsInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.delete(movieItem);
            }
        });
    }




}

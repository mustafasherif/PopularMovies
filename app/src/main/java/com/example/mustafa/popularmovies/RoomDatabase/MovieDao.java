package com.example.mustafa.popularmovies.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mustafa.popularmovies.DataItems.MovieItem;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MovieItem>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieItem movieItem);

    @Delete
    void delete(MovieItem movieItem);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieItem> getMovie(int id);

}

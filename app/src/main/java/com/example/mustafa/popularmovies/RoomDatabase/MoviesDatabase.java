package com.example.mustafa.popularmovies.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mustafa.popularmovies.DataItems.MovieItem;

@Database(entities = MovieItem.class,version = 1,exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MoviesDatabase INSTANCE;

    public static MoviesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, "movies_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

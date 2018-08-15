package com.example.mustafa.popularmovies.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseExecuter {
    private static final Object Lock=new Object();
    private static DatabaseExecuter sInstance;
    private final Executor deskIO;


    public DatabaseExecuter(Executor deskIO) {
        this.deskIO = deskIO;
    }

    public static DatabaseExecuter getsInstance(){
        if (sInstance==null){
            synchronized (Lock){
                sInstance=new DatabaseExecuter(Executors.newSingleThreadExecutor());
            }

        }

    return sInstance;
    }

    public Executor diskIo(){return deskIO;}
}

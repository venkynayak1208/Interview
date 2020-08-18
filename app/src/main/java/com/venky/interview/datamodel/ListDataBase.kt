package com.venky.interview.datamodel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MainModel::class), version = 2, exportSchema = false)
public abstract class ListDataBase : RoomDatabase() {
    abstract fun mainDao(): MainDao

    companion object {
        @Volatile
        private var INSTANCE: ListDataBase? = null
        fun getDatabase(context: Context): ListDataBase? {
            if (INSTANCE == null) {
                synchronized(ListDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, ListDataBase::class.java, "room_database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
package com.edwnmrtnz.trendingrepo.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LastRequestRow::class,
        TrendingGithubRepoRow::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TrendingRepoDatabase : RoomDatabase() {

    abstract fun lastRequestDao(): LastRequestDao

    abstract fun trendingGithubRepoDao(): TrendingGithubRepoDao

    companion object {
        @Volatile
        private var INSTANCE: TrendingRepoDatabase? = null
        fun getInstance(context: Context): TrendingRepoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrendingRepoDatabase::class.java,
                    "trendingrepo.db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

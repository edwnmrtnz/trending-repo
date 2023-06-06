package com.edwnmrtnz.trendingrepo.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingGithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rows: List<TrendingGithubRepoRow>)

    @Query("SELECT * FROM trending_github_repos")
    fun get(): List<TrendingGithubRepoRow>

    @Query("DELETE FROM trending_github_repos")
    fun clear()
}

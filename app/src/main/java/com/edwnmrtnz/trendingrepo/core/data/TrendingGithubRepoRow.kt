package com.edwnmrtnz.trendingrepo.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_github_repos")
data class TrendingGithubRepoRow(
    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val repoId: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "repo_name")
    val repoName: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "primary_language")
    val primaryLanguage: String,
    @ColumnInfo(name = "star_count")
    val starCount: Int
)

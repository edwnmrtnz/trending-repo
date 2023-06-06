package com.edwnmrtnz.trendingrepo.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LastRequestDao {

    @Query("SELECT * FROM last_request LIMIT 1")
    fun get(): LastRequestRow?

    @Query("DELETE FROM last_request")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(row: LastRequestRow)

    @Transaction
    suspend fun save(row: LastRequestRow) {
        clear()
        insert(row)
    }
}

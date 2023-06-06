package com.edwnmrtnz.trendingrepo.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "last_request")
class LastRequestRow(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "time")
    val time: Long
) {

    companion object {
        private const val DEFAULT_KEY = "key_last_request_row"
        fun create(): LastRequestRow {
            return LastRequestRow(DEFAULT_KEY, time = Date().time)
        }
    }

    fun toDate(): Date {
        return Date(time)
    }
}

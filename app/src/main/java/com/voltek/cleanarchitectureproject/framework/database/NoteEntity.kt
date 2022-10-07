package com.voltek.cleanarchitectureproject.framework.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "creation_date")
    val creationTime: Long,
    @ColumnInfo(name = "update_date")
    val updateTime: Long,
)

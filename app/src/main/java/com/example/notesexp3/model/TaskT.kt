package com.example.notesexp3.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TaskT(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "taskId")
    val id:String,
    @ColumnInfo(name = "taskTitle")
    val title:String,
    val description:String,
    val date:Date
)

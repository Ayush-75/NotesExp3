package com.example.notesexp3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesexp3.model.TaskT
import com.example.notesexp3.converters.TypeConverter
import com.example.notesexp3.dao.TaskDao

@Database(entities = [TaskT::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class TaskDatabase:RoomDatabase() {

    abstract val taskDao:TaskDao

    companion object{

        @Volatile
        private var INSTANCE:TaskDatabase? = null
        fun getInstance(context: Context):TaskDatabase{
            synchronized(this){
                return INSTANCE?:Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_db"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
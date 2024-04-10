package com.example.notesexp3.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesexp3.model.TaskT
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskT): Long

    @Query("SELECT * FROM TASKT ORDER BY date DESC")
    fun getTaskList(): Flow<List<TaskT>>

    @Delete
    suspend fun deleteTask(task: TaskT):Int

    // delete using id
    @Query("DELETE FROM TaskT WHERE taskId == :taskId")
    suspend fun deleteTaskUsingId(taskId: String):Int

    //
    @Update
    suspend fun updateTask(task: TaskT):Int

    //specific
    @Query("UPDATE TaskT SET taskTitle = :title, description = :desc WHERE taskId = :taskId")
    suspend fun updateTaskParticularField(taskId:String,title:String,desc:String):Int
}
package com.example.notesexp3.Repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.notesexp3.database.TaskDatabase
import com.example.notesexp3.model.TaskT
import com.example.notesexp3.utils.Resource
import com.example.notesexp3.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepo(application: Application) {
    private val taskDao = TaskDatabase.getInstance(application).taskDao

    fun getTaskList() = flow {
        emit(Loading())
        try {
            val result = taskDao.getTaskList()
            emit(Success(result))
        }catch (e:Exception){
            emit(Error(e.message.toString()))
        }
    }

    fun insertTask(task:TaskT) = MutableLiveData<Resource<Long>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }
    fun deleteTask(task:TaskT) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun deleteTaskUsingTaskId(taskID:String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTaskUsingId(taskID)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun updateTask(task: TaskT) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }
    fun updateTaskParticularField(taskId:String,title:String,description:String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTaskParticularField(taskId,title,description)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }


}

package com.example.notesexp3.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notesexp3.Repository.TaskRepo
import com.example.notesexp3.model.TaskT
import com.example.notesexp3.utils.Resource

class TaskViewModel(application: Application):AndroidViewModel(application) {

    private val taskRepo = TaskRepo(application)

    fun getTaskList() = taskRepo.getTaskList()

    fun insertTask(task: TaskT):MutableLiveData<Resource<Long>>{
        return taskRepo.insertTask(task)
    }
    fun deleteTask(task: TaskT):MutableLiveData<Resource<Int>>{
        return taskRepo.deleteTask(task)
    }
    fun deleteTaskUsingId(taskId:String):MutableLiveData<Resource<Int>>{
        return taskRepo.deleteTaskUsingTaskId(taskId)
    }
    fun updateTask(task: TaskT):MutableLiveData<Resource<Int>>{
        return taskRepo.updateTask(task)
    }
    fun updateTaskParticularField(taskId:String,title:String,desc:String):MutableLiveData<Resource<Int>>{
        return taskRepo.updateTaskParticularField(taskId,title,desc)
    }
}
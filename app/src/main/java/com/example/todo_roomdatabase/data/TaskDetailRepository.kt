package com.example.todo_roomdatabase.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todo_roomdatabase.data.Task
import com.example.todo_roomdatabase.data.TaskDatabase
import com.example.todo_roomdatabase.data.TaskDetailDao

class TaskDetailRepository(context :Application) {

    val taskDetailDao : TaskDetailDao = TaskDatabase.getDatabase(context).taskDetailDao()

    fun getTask(id :Long): LiveData<Task>{
        return taskDetailDao.getTask(id)
    }
    suspend fun deleteTask(task : Task){
        taskDetailDao.DeleteTask(task)
    }
    suspend fun insertTask(task: Task){
        taskDetailDao.InsertTask(task)
    }
    suspend fun updateTask(task: Task){
        taskDetailDao.UpdateTask(task)
    }
}
package com.example.todo_roomdatabase.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todo_roomdatabase.data.*

class TaskListRepository(context :Application) {
    val taskListDao : TaskListDao = TaskDatabase.getDatabase(context).taskListDao()

    fun getTask(sort : SortColumn):LiveData<List<Task>>{
        return if(sort == SortColumn.Priority){
            taskListDao.getTaskByPriority(TaskStatus.Open.ordinal)
        }
        else{
            taskListDao.getTaskByTitle(TaskStatus.Open.ordinal)
        }
    }

}
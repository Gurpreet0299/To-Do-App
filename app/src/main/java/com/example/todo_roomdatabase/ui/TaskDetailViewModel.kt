package com.example.todo_roomdatabase.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.todo_roomdatabase.data.Task
import com.example.todo_roomdatabase.data.TaskDetailRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel (application :Application):AndroidViewModel(application){

     val repo : TaskDetailRepository = TaskDetailRepository(application)

     val _taskId =MutableLiveData<Long>(0)
    val taskId :LiveData <Long>
    get() = _taskId

    val task : LiveData<Task> = Transformations.switchMap(_taskId){ id->
        repo.getTask(id)
    }
  fun setTaskId(id :Long){
      if(_taskId.value!=id){
        _taskId.value=id
      }
  }
    fun saveTask(task: Task){
        viewModelScope.launch {
           if(_taskId.value==0L){
               repo.insertTask(task)
           }
            else{
               repo.updateTask(task)
           }
        }
    }
    fun deleteTask(){
        viewModelScope.launch {
              task.value?.let { repo.deleteTask(it) }
        }
    }
}
package com.example.todo_roomdatabase.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.todo_roomdatabase.data.SortColumn
import com.example.todo_roomdatabase.data.Task
import com.example.todo_roomdatabase.data.TaskListRepository

class TaskListViewModel(application: Application) :AndroidViewModel(application){

    val repo : TaskListRepository = TaskListRepository(application)
    val _sortedOrder = MutableLiveData<SortColumn>(SortColumn.Priority)

    val tasks : LiveData<List<Task>> = Transformations.switchMap(_sortedOrder){
        repo.getTask(_sortedOrder.value!!)
    }

    fun changeSortedOrder(sort: SortColumn){
        _sortedOrder.value=sort
    }
}
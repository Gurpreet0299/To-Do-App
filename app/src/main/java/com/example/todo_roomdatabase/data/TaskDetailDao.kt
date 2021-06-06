package com.example.todo_roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo_roomdatabase.data.Task

@Dao
interface TaskDetailDao {
    @Query("SELECT * FROM task WHERE `id`= :id")
    fun getTask(id :Long) :LiveData<Task>

    @Insert(onConflict=OnConflictStrategy.IGNORE )
    suspend fun InsertTask(task : Task) : Long

    @Update
    suspend fun UpdateTask(task: Task)

    @Delete
    suspend fun DeleteTask(task : Task)
}
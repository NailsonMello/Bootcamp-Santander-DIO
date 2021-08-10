package com.nailson.todolist.santander.bootcamp.data.repository

import androidx.lifecycle.LiveData
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task

interface TaskRepository {
    fun getAllTask(): LiveData<List<Task>>
    fun getTaskById(id: Int): LiveData<Task>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(id: Int)
}
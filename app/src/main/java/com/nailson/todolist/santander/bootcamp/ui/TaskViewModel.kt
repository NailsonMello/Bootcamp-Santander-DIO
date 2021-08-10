package com.nailson.todolist.santander.bootcamp.ui

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task
import com.nailson.todolist.santander.bootcamp.data.repository.TaskRepositoryImpl


class TaskViewModel @ViewModelInject constructor(private val repository: TaskRepositoryImpl) : ViewModel() {
    suspend fun insertTask(task: Task) = repository.insertTask(task)
    suspend fun updateTask(task: Task) = repository.updateTask(task)
    suspend fun deleteTask(id: Int) = repository.deleteTaskById(id)
    fun getAllTask() = repository.getAllTask()
    fun getTaskById(id : Int) = repository.getTaskById(id)
}
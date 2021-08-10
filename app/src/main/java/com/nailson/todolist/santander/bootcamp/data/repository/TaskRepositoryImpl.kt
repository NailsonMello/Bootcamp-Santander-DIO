package com.nailson.todolist.santander.bootcamp.data.repository

import com.nailson.todolist.santander.bootcamp.data.db.dao.TaskDAO
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDAO) : TaskRepository {

    override fun getAllTask() = taskDao.getAll()
    override fun getTaskById(id: Int) = taskDao.findById(id)
    override suspend fun insertTask(task: Task) = taskDao.insert(task)
    override suspend fun updateTask(task: Task) = taskDao.update(task)
    override suspend fun deleteTaskById(id: Int) = taskDao.delete(id)
}
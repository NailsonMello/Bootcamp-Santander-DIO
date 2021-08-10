package com.nailson.todolist.santander.bootcamp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nailson.todolist.santander.bootcamp.data.db.dao.TaskDAO
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task

@Database(entities = [Task::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDAO

    companion object {
        const val DB_NAME = "task_database.db"
    }
}
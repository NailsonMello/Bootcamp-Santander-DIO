package com.nailson.todolist.santander.bootcamp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM task ORDER BY date")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun findById(id: Int) : LiveData<Task>
}
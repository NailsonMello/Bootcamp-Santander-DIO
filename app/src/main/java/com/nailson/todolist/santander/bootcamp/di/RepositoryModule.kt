package com.nailson.todolist.santander.bootcamp.di

import com.nailson.todolist.santander.bootcamp.data.db.dao.TaskDAO
import com.nailson.todolist.santander.bootcamp.data.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesTaskRepository(taskDao: TaskDAO) = TaskRepositoryImpl(taskDao)
}
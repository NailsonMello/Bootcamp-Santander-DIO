package com.nailson.todolist.santander.bootcamp.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val hour: String,
    val date: String,
    val cancelled: Boolean = false,
    val concluded: Boolean = false
) : Parcelable
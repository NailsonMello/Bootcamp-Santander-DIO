package com.nailson.todolist.santander.bootcamp.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.nailson.todolist.santander.bootcamp.R
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task
import com.nailson.todolist.santander.bootcamp.databinding.ActivityAddTaskBinding
import com.nailson.todolist.santander.bootcamp.extensions.Coroutines
import com.nailson.todolist.santander.bootcamp.extensions.format
import com.nailson.todolist.santander.bootcamp.extensions.myToast
import com.nailson.todolist.santander.bootcamp.extensions.text
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            binding.btnNewTask.text = getString(R.string.label_update_task)
            val taskId = intent.getIntExtra(TASK_ID, 0)

            viewModel.getTaskById(taskId).observe(this){
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            saveData()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun saveData() {
        val title = binding.tilTitle.text
        val date = binding.tilDate.text
        val hour = binding.tilHour.text
        val id = intent.getIntExtra(TASK_ID, 0)

        if (title.isEmpty() || date.isEmpty() || hour.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }
        val task = Task(title = title, date = date, hour = hour, id = id)
        Coroutines.main {
            if (intent.hasExtra(TASK_ID)) {
                viewModel.updateTask(task).also {
                    myToast(getString(R.string.success_update))
                }

            } else {
                viewModel.insertTask(task).also {
                    myToast(getString(R.string.success_save))
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val TASK_ID = "task_id"
    }

}
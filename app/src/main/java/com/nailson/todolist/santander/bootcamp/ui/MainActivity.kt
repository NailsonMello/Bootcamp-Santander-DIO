package com.nailson.todolist.santander.bootcamp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nailson.todolist.santander.bootcamp.R
import com.nailson.todolist.santander.bootcamp.data.db.entity.Task
import com.nailson.todolist.santander.bootcamp.databinding.ActivityMainBinding
import com.nailson.todolist.santander.bootcamp.extensions.Coroutines
import com.nailson.todolist.santander.bootcamp.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = TaskListAdapter()
        binding.rvTasks.adapter = adapter

        updateList()
        insertListeners()
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(binding.rvTasks)
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            Coroutines.main {
                viewModel.deleteTask(it.id).also {
                    myToast(getString(R.string.success_delete))
                }
            }
            updateList()
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Coroutines.main {
                    val task = adapter.getTaskAt(viewHolder.adapterPosition)
                    when (direction) {
                        8 -> cancelTask(task)
                        4 -> concludeTask(task)
                    }
                }
                updateList()
            }
        }
    }

    private fun cancelTask(task: Task) {
        val taskCancelled = Task(title = task.title, date = task.date, hour = task.hour, id = task.id, cancelled = true)
        Coroutines.main {
            viewModel.updateTask(taskCancelled).also {
                myToast(getString(R.string.success_cancelled))
            }
        }
    }

    private fun concludeTask(task: Task) {
        val taskConcluded = Task(title = task.title, date = task.date, hour = task.hour, id = task.id, concluded = true)
        Coroutines.main {
            viewModel.updateTask(taskConcluded).also {
                myToast(getString(R.string.success_concluded))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        Coroutines.main {
            viewModel.getAllTask().observe(this) {
                it.let {
                    binding.includeEmpty.emptyState.visibility = if (it.isEmpty())
                        View.VISIBLE else View.GONE

                    adapter.setData(it)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }


}
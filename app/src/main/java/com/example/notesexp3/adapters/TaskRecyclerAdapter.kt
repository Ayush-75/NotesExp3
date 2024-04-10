package com.example.notesexp3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesexp3.databinding.ViewTaskLayoutBinding
import com.example.notesexp3.model.TaskT
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRecyclerAdapter(
    private val deleteUpdateCallback: (type: String, position: Int, task: TaskT) -> Unit
) :
    RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {

    private val taskList = arrayListOf<TaskT>()

    class ViewHolder(val binding: ViewTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskRecyclerAdapter.ViewHolder {
        val binding =
            ViewTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskRecyclerAdapter.ViewHolder, position: Int) {
        val task = taskList[position]
        with(holder) {
            binding.titleTxt.text = task.title
            binding.descrTxt.text = task.description


            val dateFormat = SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss a", Locale.getDefault())

            binding.dateTxt.text = dateFormat.format(task.date)

            binding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", position, task)
                }
            }
                binding.editImg.setOnClickListener {
                    if (adapterPosition != -1) {
                        deleteUpdateCallback("update", position, task)
                    }
                }
            }
    }

    fun addAllTask(newTaskList: List<TaskT>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }



}
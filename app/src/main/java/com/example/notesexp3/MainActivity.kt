package com.example.notesexp3

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesexp3.adapters.TaskRecyclerAdapter
import com.example.notesexp3.databinding.ActivityMainBinding
import com.example.notesexp3.databinding.AddTaskDialogBinding
import com.example.notesexp3.databinding.LoadingDialogBinding
import com.example.notesexp3.databinding.UpdateTaskDialogBinding
import com.example.notesexp3.model.TaskT
import com.example.notesexp3.utils.Status
import com.example.notesexp3.utils.clearEditText
import com.example.notesexp3.utils.longToastShow
import com.example.notesexp3.utils.setupDialog
import com.example.notesexp3.utils.validateEditText
import com.example.notesexp3.viewModels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val addTaskDialogBinding: AddTaskDialogBinding by lazy {
        AddTaskDialogBinding.inflate(layoutInflater)
    }
    private val updateTaskDialogBinding: UpdateTaskDialogBinding by lazy {
        UpdateTaskDialogBinding.inflate(layoutInflater)
    }

    private val loadingDialogBinding: LoadingDialogBinding by lazy {
        LoadingDialogBinding.inflate(layoutInflater)
    }

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }

    private val addTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
//            setContentView(addTaskDialogBinding.root)
            setupDialog(addTaskDialogBinding.root)
        }
    }
    private val updateTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
//            setContentView(updateTaskDialogBinding.root)
            setupDialog(updateTaskDialogBinding.root)
        }
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
//            setupDialog(R.layout.loading_dialog)
            setupDialog(loadingDialogBinding.root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // add task start
        addTaskDialogBinding.closeImg.setOnClickListener { addTaskDialog.dismiss() }


        val addEditTitle = addTaskDialogBinding.edTaskTitle
        val addEditTitleL = addTaskDialogBinding.edTaskTitleL

        addTaskDialogBinding.edTaskTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validateEditText(addEditTitle, addEditTitleL)
            }
        })

        val addEditDesc = addTaskDialogBinding.edTaskDesc
        val addEditDescL = addTaskDialogBinding.edTaskDescL


        addEditDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validateEditText(addEditDesc, addEditDescL)
            }
        })


        binding.addTaskFab.setOnClickListener {
            clearEditText(addEditTitle, addEditTitleL)
            clearEditText(addEditDesc, addEditDescL)
            addTaskDialog.show()
        }

        addTaskDialogBinding.saveTaskBtn.setOnClickListener {
            if (validateEditText(addEditTitle, addEditTitleL) && validateEditText(
                    addEditDesc,
                    addEditDescL
                )
            ) {
                addTaskDialog.dismiss()
                val newTask = TaskT(
                    UUID.randomUUID().toString(),
                    addEditTitle.text.toString().trim(),
                    addEditDesc.text.toString().trim(),
                    Date()
                )
                taskViewModel.insertTask(newTask).observe(this) {
                    when (it.status) {
                        Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            if (it.data?.toInt() != -1) {
                                longToastShow("Task Added Successfully")
                            }
                        }

                        Status.ERROR -> {
                            loadingDialog.dismiss()
                            it.message?.let { it1 -> longToastShow(it1) }
                        }
                    }
                }

//                Toast.makeText(this, "Validated!!", Toast.LENGTH_LONG).show()
//                loadingDialog.show()

            }


        }
        // add task ends

        //update task start
        updateTaskDialogBinding.closeImg.setOnClickListener { updateTaskDialog.dismiss() }

        val updateEditTitle = updateTaskDialogBinding.edTaskTitle
        val updateEditTitleL = updateTaskDialogBinding.edTaskTitleL

        updateEditTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validateEditText(updateEditTitle, updateEditTitleL)
            }
        })

        val updateEditDesc = updateTaskDialogBinding.edTaskDesc
        val updateEditDescL = updateTaskDialogBinding.edTaskDescL

        updateEditDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validateEditText(updateEditDesc, updateEditDescL)
            }
        })



        // update task end

        val taskRecyclerAdapter = TaskRecyclerAdapter { type, position, task ->
            if (type=="delete") {
                taskViewModel
                    .deleteTaskUsingId(task.id)
                    .observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                loadingDialog.show()
                            }

                            Status.SUCCESS -> {
                                loadingDialog.dismiss()
                                if (it.data != -1) {
                                    longToastShow("Task Deleted Successfully")
                                }
                            }

                            Status.ERROR -> {
                                loadingDialog.dismiss()
                                it.message?.let { it1 -> longToastShow(it1) }
                            }
                        }
                    }
            }
            else if (type == "update"){
                updateEditTitle.setText(task.title)
                updateEditDesc.setText(task.description)
                updateTaskDialogBinding.updateTaskBtn.setOnClickListener {
                    if (validateEditText(updateEditTitle, updateEditTitleL) &&

                        validateEditText(
                            updateEditDesc, updateEditDescL
                        )
                    ) {
                        val updateTask = TaskT(
                            task.id,
                            updateEditTitle.text.toString().trim(),
                            updateEditDesc.text.toString().trim(),
                            //date
                            Date()
                        )
                        updateTaskDialog.dismiss()
                        loadingDialog.show()
                        taskViewModel
                            .updateTask(updateTask)
                            .observe(this) {
                                when (it.status) {
                                    Status.LOADING -> {
                                        loadingDialog.show()
                                    }

                                    Status.SUCCESS -> {
                                        loadingDialog.dismiss()
                                        if (it.data != -1) {
                                            longToastShow("Task Updated Successfully")
                                        }
                                    }

                                    Status.ERROR -> {
                                        loadingDialog.dismiss()
                                        it.message?.let { it1 -> longToastShow(it1) }
                                    }
                                }
                            }
                    }
                }
                updateTaskDialog.show()
            }
        }

        binding.taskRV.adapter = taskRecyclerAdapter


        callGetTaskList(taskRecyclerAdapter)

    }

    private fun callGetTaskList(taskRecyclerAdapter:TaskRecyclerAdapter) {
        loadingDialog.show()
        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.getTaskList().collect {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                        it.data?.collect { taskList ->
                            loadingDialog.dismiss()
                            taskRecyclerAdapter.addAllTask(taskList)
                        }
                    }

                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
        }
    }
}
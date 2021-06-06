package com.example.todo_roomdatabase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todo_roomdatabase.data.PriorityLevel
import com.example.todo_roomdatabase.R
import com.example.todo_roomdatabase.data.Task
import com.example.todo_roomdatabase.data.TaskStatus
import kotlinx.android.synthetic.main.fragment_task_detail.*

class TaskDetailFragment : Fragment() {

    lateinit var viewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TaskDetailViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val priorities = mutableListOf<String>()
        PriorityLevel.values().forEach { priorities.add(it.name) }
        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        //Putting items in spinner
        priority_spinner.adapter = arrayAdapter
        priority_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            //when click on one item at spinner
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateTaskPriorityView(position)
            }
        }
       //id of item you clicked in list on TaskListFragment
        val id = TaskDetailFragmentArgs.fromBundle(requireArguments()).id
        //changing id to id of item you clicked
        viewModel.setTaskId(id)
        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let {
              setData(it)
            }
        })
        save_task.setOnClickListener {
            saveTask()
        }
        delete_task.setOnClickListener {
            deleteTask()
        }
    }
    private fun deleteTask() {
        viewModel.deleteTask()

        requireActivity().onBackPressed()
    }

    private fun saveTask() {
        val title=task_title.text.toString()
        val detail=task_detail.text.toString()
        val priority=priority_spinner.selectedItemPosition
        val selectedStatusButton=radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        var status=TaskStatus.Open.ordinal
        if(selectedStatusButton.text==TaskStatus.Close.name){
           status=TaskStatus.Close.ordinal
        }
        val task=Task(viewModel.taskId.value!!,title,detail,priority,status)
        viewModel.saveTask(task)
        requireActivity().onBackPressed()
    }

    fun updateTaskPriorityView(priority: Int) {
        when (priority) {
            PriorityLevel.High.ordinal -> task_priority.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorPriorityHigh))
            PriorityLevel.Medium.ordinal -> task_priority.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorPriorityMedium))
            PriorityLevel.Low.ordinal -> task_priority.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorPriorityLow))
        }
    }

    fun setData(task: Task) {
        updateTaskPriorityView(task.priority)
        task_title.setText(task.title)
        task_detail.setText(task.detail)
        if (task.status == TaskStatus.Open.ordinal) {
                open_radioBtn.isChecked=true
        }
        else {
            close_radioBtn.isChecked = true
        }
        priority_spinner.setSelection(task.priority)
    }
}
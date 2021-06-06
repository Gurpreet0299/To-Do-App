package com.example.todo_roomdatabase.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_roomdatabase.R
import com.example.todo_roomdatabase.data.SortColumn
//import com.example.todo_roomdatabase.TaskListFragmentDirections
import kotlinx.android.synthetic.main.fragment_task_list.*


class TaskListFragment : Fragment() {

    lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        viewModel= ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(task_list){
             layoutManager=LinearLayoutManager(activity)
           adapter= TaskAdapter {
               findNavController().navigate(TaskListFragmentDirections.actionTaskListToTaskDetail(it))
           }
            setHasFixedSize(true)
        }
        add_btn.setOnClickListener {
            findNavController().navigate(TaskListFragmentDirections.actionTaskListToTaskDetail(0))
        }
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            (task_list.adapter as TaskAdapter).submitList(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         inflater.inflate(R.menu.my_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_sort_priority -> {
                viewModel.changeSortedOrder(SortColumn.Priority)
                item.isChecked=true
                true
            }
            R.id.menu_sort_title ->{
                viewModel.changeSortedOrder(SortColumn.Title)
                item.isChecked=true
            true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}
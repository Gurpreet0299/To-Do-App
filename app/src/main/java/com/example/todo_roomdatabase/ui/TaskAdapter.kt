package com.example.todo_roomdatabase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_roomdatabase.R
import com.example.todo_roomdatabase.data.PriorityLevel
import com.example.todo_roomdatabase.data.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*

class TaskAdapter(val listener :(Long)->Unit) :ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback()) {
    inner class TaskViewHolder(override val containerView: View) :RecyclerView.ViewHolder(containerView),LayoutContainer{
init {
    itemView.setOnClickListener {
        listener.invoke(getItem(adapterPosition).id)
    }
}
        fun bind(task : Task){
          with(task){
              when(task.priority){
                  PriorityLevel.High.ordinal ->{
                     task_priority.setBackgroundColor(ContextCompat.getColor(containerView.context, R.color.colorPriorityHigh))
                  }
                  PriorityLevel.Medium.ordinal ->{
                      task_priority.setBackgroundColor(ContextCompat.getColor(containerView.context, R.color.colorPriorityMedium))
                  }
                  else->{
                      task_priority.setBackgroundColor(ContextCompat.getColor(containerView.context, R.color.colorPriorityLow))
                  }
              }
              task_title.text=task.title
              task_detail.text=task.detail
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        return   holder.bind(getItem(position)) }

}
class DiffCallback :DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
         return oldItem==newItem
    }
}
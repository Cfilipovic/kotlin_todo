package com.connorfilipovic.todo.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_grid_todo_item.view.*

class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemTitleView: TextView? = null

    init {
        itemTitleView = itemView.item_title
    }
}
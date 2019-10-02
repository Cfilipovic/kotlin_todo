package com.connorfilipovic.todo.todolist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_grid_todo_item.view.*

class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemTitleView: TextView? = null
    var itemCard: CardView? = null
    var itemCompletedIcon: ImageView? = null

    init {
        itemTitleView = itemView.item_title
        itemCard = itemView.item_card
        itemCompletedIcon = itemView.completed_icon
    }
}
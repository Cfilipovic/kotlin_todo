package com.connorfilipovic.todo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.connorfilipovic.todo.R
import com.connorfilipovic.todo.model.TodoItemModel

class TodoListGridRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfItems = mutableListOf<TodoItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //inflate the item view and grab the layout parameters
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item_grid_todo_item, parent, false)
        val layoutParams = itemView.layoutParams

        //force the layout to shrink the height of each item to 30% of the total screen height
        layoutParams.height = (parent.height * 0.3).toInt()
        itemView.layoutParams = layoutParams

        return TodoListViewHolder(itemView)
    }

    override fun getItemCount(): Int = listOfItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as TodoListViewHolder

        itemViewHolder.itemTitleView?.text = listOfItems[position].itemTitle
    }

    fun addTodoItem(todoItem: TodoItemModel) {
        this.listOfItems.add(todoItem)
        notifyDataSetChanged()
    }

    fun removeTodoItem(todoItem: TodoItemModel) {
        this.listOfItems.remove(todoItem)
        notifyDataSetChanged()
    }

    fun removeTodoItem(position: Int) {
        this.listOfItems.removeAt(position)
        notifyDataSetChanged()
    }
}
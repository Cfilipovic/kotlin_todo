package com.connorfilipovic.todo.todolist

import android.view.LayoutInflater
import android.graphics.Paint
import android.util.Log
import android.view.View
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
        itemViewHolder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val item = listOfItems.get(position)

                //item has been completed
                if(!item.completed) {
                    item.completed = true

                    itemViewHolder.itemTitleView?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                    itemViewHolder.itemCompletedIcon!!.setImageResource(R.drawable.ic_check_circle)
                }
                else {
                    item.completed = false

                    itemViewHolder.itemTitleView?.paintFlags = itemViewHolder.itemTitleView!!.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                    itemViewHolder.itemCompletedIcon!!.setImageResource(R.drawable.ic_radio_button_unchecked)
                }
            }
        })
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
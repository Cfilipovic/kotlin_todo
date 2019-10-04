package com.connorfilipovic.todo.todolist

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.connorfilipovic.todo.MainActivity
import com.connorfilipovic.todo.R
import com.connorfilipovic.todo.model.TodoItemModel
import com.connorfilipovic.todo.model.TodoListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListGridRecyclerAdapter(val activity : Activity, val date : String):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listOfItems = mutableListOf<TodoItemModel>()

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

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        activity!!.tv_no_items.visibility = View.GONE
    }

    override fun getItemCount(): Int = listOfItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as TodoListViewHolder
        val item = listOfItems.get(position)

        itemViewHolder.itemTitleView?.text = listOfItems[position].itemTitle
        if(item.completed) {
            itemViewHolder.itemTitleView?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemViewHolder.itemCompletedIcon?.setImageResource(R.drawable.ic_check_circle)
        }
        else {
            itemViewHolder.itemTitleView?.paintFlags = itemViewHolder.itemTitleView!!.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            itemViewHolder.itemCompletedIcon?.setImageResource(R.drawable.ic_radio_button_unchecked)
        }


        itemViewHolder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //item has been completed
                if(!item.completed) {
                    item.completed = true

                    //update the todolist db object
                    storeTodoList()

                    itemViewHolder.itemTitleView?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                    itemViewHolder.itemCompletedIcon?.setImageResource(R.drawable.ic_check_circle)
                }
                else {
                    item.completed = false

                    //update the todolist db object
                    storeTodoList()

                    itemViewHolder.itemTitleView?.paintFlags = itemViewHolder.itemTitleView!!.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                    itemViewHolder.itemCompletedIcon?.setImageResource(R.drawable.ic_radio_button_unchecked)
                }
            }
        })
    }

    fun addTodoItem(todoItem: TodoItemModel) {
        activity!!.tv_no_items.visibility = View.VISIBLE
        activity!!.tv_no_items.visibility = View.GONE
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

    private fun storeTodoList() {
        //store the todo list under the user
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(user?.uid.toString()).collection("lists").document(date).set(
            TodoListModel(listOfItems)
        )
    }
}
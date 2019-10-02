package com.connorfilipovic.todo.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.connorfilipovic.todo.R
import com.connorfilipovic.todo.model.TodoItemModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_todo_list.*
import java.util.*


class TodoListFragment : Fragment() {

    companion object {
        fun newInstance(date: String) = TodoListFragment().apply {
            arguments = Bundle().apply {
                putString("DATE", date)
            }
        }
    }

    val cal = Calendar.getInstance()
    lateinit var date: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_todo_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize recycler view
        initView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("DATE")?.let {
            date = it

            //update the toolbar title
            activity!!.toolbar.setTitle("${date} Todo List")
        }
    }

    private fun initView() {
        rv_todo_list.layoutManager = GridLayoutManager(context, 2)

        val todoListAdapter = TodoListGridRecyclerAdapter()
        rv_todo_list.adapter = todoListAdapter

        val itemSwipeHandler = ItemTouchHelper(SwipeToDeleteCallback(todoListAdapter, context!!))
        itemSwipeHandler.attachToRecyclerView(rv_todo_list)

        //add dummy data for temp
        //TODO: actually add data in the future

        for(x in 0..10) {
            todoListAdapter.addTodoItem(TodoItemModel("Test: " + x))
        }

        //register add new todo item FAB
        btnAddTodoItem.setOnClickListener {
            Toast.makeText(context, "FAB is clicked...", Toast.LENGTH_LONG).show()
        }
    }
}
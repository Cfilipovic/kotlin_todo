package com.connorfilipovic.todo.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.connorfilipovic.todo.MainActivity
import com.connorfilipovic.todo.R
import com.connorfilipovic.todo.model.TodoItemModel
import com.connorfilipovic.todo.model.TodoListModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_input_todo_item.view.*
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
    lateinit var todoListAdapter: TodoListGridRecyclerAdapter


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
            (activity as MainActivity).toolbar.setTitle("${date} Todo List")
        }
    }

    override fun onResume() {
        super.onResume()
        tv_no_items.visibility = View.VISIBLE
        tv_no_items.visibility = View.GONE
    }

    private fun initView() {
        rv_todo_list.layoutManager = GridLayoutManager(context, 2)

        todoListAdapter = TodoListGridRecyclerAdapter(activity!!, date)
        rv_todo_list.adapter = todoListAdapter

        val itemSwipeHandler = ItemTouchHelper(SwipeToDeleteCallback(todoListAdapter, context!!, date, tv_no_items))
        itemSwipeHandler.attachToRecyclerView(rv_todo_list)

        //retrieve todo list
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(user?.uid.toString()).collection("lists").document(date)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.data != null) {
                    val todoList = documentSnapshot.toObject(TodoListModel::class.java)

                    if(!todoList!!.todoList.isEmpty()) {
                        for (todoItem in todoList!!.todoList) {
                            todoListAdapter.addTodoItem(todoItem)
                        }


                        tv_no_items.visibility = View.GONE
                    }
                    else {
                        tv_no_items.visibility = View.VISIBLE
                    }

                    progress_bar.visibility = View.GONE
                } else {
                    progress_bar.visibility = View.GONE
                    tv_no_items.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TodoListFragment", "get failed with ", exception)
            }

        //register add new todo item FAB
        btn_add_todo_item.setOnClickListener {
            showCreateTodoItemDialog()
        }
    }

    fun showCreateTodoItemDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("New Todo Item")

        val view = layoutInflater.inflate(R.layout.dialog_input_todo_item, null)

        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->

        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        val alertDialog = builder.show()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newTodoItemText = view.todoItemEditText.text.toString()
            var isValid = true
            if (newTodoItemText.isBlank()) {
                view.todoItemEditText.error = "Error task required"
                isValid = false
            }

            if (isValid) {
                val todoItem = TodoItemModel(newTodoItemText, false)
                //at least one item has been created
                tv_no_items.visibility = View.GONE

                todoListAdapter.addTodoItem(todoItem)
                todoListAdapter.notifyDataSetChanged()

                //update the todolist db object
                storeTodoList()

                Snackbar.make(getView()!!, "Todo item successfully added!", Snackbar.LENGTH_SHORT).show()

                alertDialog.dismiss()
            }
        }
    }

    private fun storeTodoList() {
        //store the todo list under the user
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(user?.uid.toString()).collection("lists").document(date).set(TodoListModel(todoListAdapter.listOfItems))
    }
}
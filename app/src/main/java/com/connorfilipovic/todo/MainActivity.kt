package com.connorfilipovic.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.connorfilipovic.todo.model.TodoItemModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        initView()
    }

    private fun initView() {
        rv_todo_list.layoutManager = GridLayoutManager(this, 2)

//        rv_todo_list.addItemDecoration(GridItemDecoration(10, 2))

        val todoListAdapter = TodoListGridRecyclerAdapter()
        rv_todo_list.adapter = todoListAdapter

        //add dummy data for temp
        //TODO: actually add data in the future

        for(x in 0..10) {
            todoListAdapter.addTodoItem(TodoItemModel("Test: " + x))
        }
    }

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_calendar -> {
            // User chose the "Print" item
            Toast.makeText(this,"Calendar action",Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}

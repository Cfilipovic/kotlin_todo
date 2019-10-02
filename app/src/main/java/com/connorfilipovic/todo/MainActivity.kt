package com.connorfilipovic.todo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.connorfilipovic.todo.datepicker.DatePickerFragment
import com.connorfilipovic.todo.todolist.TodoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is required otherwise the application will overwrite the proper toolbar title with the desired one
        //This is some stupid shit right here
        //https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown/32582780
        toolbar.setTitle("")

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_layout, TodoListFragment.newInstance(getCurrentDate()), "todoList").commit()
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
            val newFragment = DatePickerFragment()
            // Show the date picker dialog
            newFragment.show(supportFragmentManager, "Date Picker")
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun getCurrentDate():String {
        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return formatDate(year, month, day)
    }

    //Format date
    private fun formatDate(year:Int, month:Int, day:Int):String{
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.LONG)
        return df.format(chosenDate)
    }
}

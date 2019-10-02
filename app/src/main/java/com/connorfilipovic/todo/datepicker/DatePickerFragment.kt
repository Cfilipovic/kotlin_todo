package com.connorfilipovic.todo.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.connorfilipovic.todo.R
import com.connorfilipovic.todo.todolist.TodoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar:Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()

        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //Launch the date picker dialog
        return DatePickerDialog(
            context!!,
//            R.style.AppTheme,
            this,
            year,
            month,
            day
        )
    }


    //Listener for date set
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, TodoListFragment.newInstance(formatDate(year,month,day)), "todoList").commit()
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
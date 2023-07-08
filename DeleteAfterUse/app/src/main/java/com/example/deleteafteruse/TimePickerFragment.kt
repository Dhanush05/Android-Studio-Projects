package com.example.deleteafteruse

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val cal = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hr = Calendar.HOUR_OF_DAY
        val min = Calendar.MINUTE
        return  TimePickerDialog(activity,this,hr,min,true)
    }
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        Toast.makeText(activity, "Time Picked Succesfully "+p1+":"+p2, Toast.LENGTH_SHORT).show()
    }

}
package com.example.deleteafteruse

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment:DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = Calendar.YEAR
        val month = Calendar.MONTH
        val date = Calendar.DATE
        return  DatePickerDialog(requireContext(),this,year,month,date)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        Toast.makeText(activity,"Date: "+p3+"/"+(p2+1)+"/"+p1,Toast.LENGTH_SHORT).show()
    }
}
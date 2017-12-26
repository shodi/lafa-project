package com.example.demo.view

import java.util.Date
import java.util.Calendar   

class PDFGenerator (customerName: String) {

    public var fileName: String

    init {
        var cal: Calendar = Calendar.getInstance()
        fileName = "${customerName}_"
        fileName += "${cal.get(Calendar.DAY_OF_MONTH)}.${cal.get(Calendar.MONTH)}.${cal.get(Calendar.YEAR)}_"
        fileName += "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
    }

}
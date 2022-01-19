package com.example.car_spec.unittest

import java.text.SimpleDateFormat
import java.util.*

class DateConverterTest {


        //------------date converter class to convert String to long from string------------//
        fun converTotLong(time: String): String {
            val date = Date(time.toLong()*1000L)
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(date)
        }

}
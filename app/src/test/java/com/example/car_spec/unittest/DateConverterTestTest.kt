package com.example.car_spec.unittest

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DateConverterTestTest{

    private lateinit var dateConverter : DateConverterTest
    @Before
    fun setup(){
        dateConverter = DateConverterTest()
        // dateConverter = DateConverter()
    }
    //returns valid date
    @Test
    fun dateFormatIsValidwithvalidFormatThenReturnTrue(){

        val date = dateConverter.converTotLong("1642617919")
        Assert.assertEquals("2022-01-19 21:38:34", date)
    }

    //returns invalid date and throw exception
    @Test
    fun dateFormatIsValidWithInvalidFormatThenReturnExeption(){

        Assert.assertThrows(Exception::class.java , {
            dateConverter.converTotLong("xfhfghfgh")

        })
    }

}
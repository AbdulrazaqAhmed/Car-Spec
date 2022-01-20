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

        val date = dateConverter.converTotLong("1642661067")
        Assert.assertEquals("2022-01-20 09:44:27", date)
    }

    //returns invalid date and throw exception
    @Test
    fun dateFormatIsValidWithInvalidFormatThenReturnExeption(){

        Assert.assertThrows(Exception::class.java , {
            dateConverter.converTotLong("llllllllhhh")

        })
    }

}
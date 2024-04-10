package com.example.notesexp3.converters

import androidx.room.TypeConverter
import java.util.Date

class TypeConverter {

    @TypeConverter
    fun fromTimeStamp(value:Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateTimeStamp(date: Date):Long{
        return date.time
    }


}
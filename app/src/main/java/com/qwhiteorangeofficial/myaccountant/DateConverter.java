package com.qwhiteorangeofficial.myaccountant;


import android.os.Build;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DateConverter{
    @TypeConverter
    public static LocalDate toLocalDateDate(String localDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate == null ? null : LocalDate.parse(localDate);
        }
        return null;
    }

    @TypeConverter
    public static String toString(LocalDate date) {
        return date == null ? null : String.valueOf(date);
    }
}

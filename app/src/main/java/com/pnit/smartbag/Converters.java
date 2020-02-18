package com.pnit.smartbag;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}

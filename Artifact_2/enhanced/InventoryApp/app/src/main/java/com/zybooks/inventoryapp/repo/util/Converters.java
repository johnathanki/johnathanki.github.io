package com.zybooks.inventoryapp.repo.util;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * A converter class to properly convert a Long data type into a Date data type
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

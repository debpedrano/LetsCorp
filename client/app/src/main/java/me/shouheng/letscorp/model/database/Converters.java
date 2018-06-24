package me.shouheng.letscorp.model.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * @author shouh
 * @version $Id: Converters, v 0.1 2018/6/24 13:12 shouh Exp$
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

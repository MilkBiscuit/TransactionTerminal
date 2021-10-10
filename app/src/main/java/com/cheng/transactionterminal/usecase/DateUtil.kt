package com.cheng.transactionterminal.usecase

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    private const val TAG = "DateUtil";
    private const val FORMAT_YEAR_MONTH_DATE = "yyyy-MM-dd";
    private const val FORMAT_Y_M_D_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    fun formatDate(date: Date, dateFormat: String = FORMAT_Y_M_D_DATE_TIME): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault());

        return formatter.format(date)
    }

    fun getToday(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }

    fun getTomorrow(): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getToday().time + DateUtils.DAY_IN_MILLIS

        return calendar.time
    }

    fun isSameDay(date: Date, compareDate: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareDate

        return calendar.get(Calendar.YEAR) == compareCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == compareCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == compareCalendar.get(Calendar.DATE)
    }

}

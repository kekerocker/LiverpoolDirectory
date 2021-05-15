package com.dsoft.liverpooldirectory.other

import java.util.*
import java.util.concurrent.TimeUnit

fun getTime(milliSeconds: Long): String {
    val minutesInHour = 60
    val hoursInDay = 24
    val avgDaysInMonth = 30

    val currentTime = Calendar.getInstance().timeInMillis
    val timeSinceCreated = currentTime - milliSeconds
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeSinceCreated)
    val hours = TimeUnit.MILLISECONDS.toHours(timeSinceCreated)
    val days = TimeUnit.HOURS.toDays(hours)

    if (minutes < minutesInHour) {
        return when (minutes) {
            1L -> "$minutes минуту назад"
            2L -> "$minutes минуты назад"
            3L -> "$minutes минуты назад"
            4L -> "$minutes минуты назад"
            21L -> "$minutes минуту назад"
            22L -> "$minutes минуты назад"
            23L -> "$minutes минуты назад"
            24L -> "$minutes минуты назад"
            31L -> "$minutes минуту назад"
            32L -> "$minutes минуты назад"
            33L -> "$minutes минуты назад"
            34L -> "$minutes минуты назад"
            41L -> "$minutes минуту назад"
            42L -> "$minutes минуты назад"
            43L -> "$minutes минуты назад"
            44L -> "$minutes минуты назад"
            51L -> "$minutes минуту назад"
            52L -> "$minutes минуты назад"
            53L -> "$minutes минуты назад"
            54L -> "$minutes минуты назад"
            else -> "$minutes минут назад"
        }
    }
    if (hours < hoursInDay) {
        return when (hours) {
            1L -> "$hours час назад"
            2L -> "$hours часа назад"
            3L -> "$hours часа назад"
            4L -> "$hours часа назад"
            21L -> "$hours час назад"
            22L -> "$hours часа назад"
            23L -> "$hours часа назад"
            else -> "$hours часов назад"
        }
    }
    if (hours > hoursInDay) {
        return when (days) {
            1L -> "$days день назад"
            2L -> "$days дня назад"
            3L -> "$days дня назад"
            4L -> "$days дня назад"
            21L -> "$days день назад"
            22L -> "$days дня назад"
            23L -> "$days дня назад"
            24L -> "$days дня назад"
            31L -> "$days день назад"
            else -> "$days дней назад"
        }
    }
    if (days > avgDaysInMonth) {
        return when (days) {
            1L -> "$days месяц назад"
            2L -> "$days месяца назад"
            3L -> "$days месяца назад"
            4L -> "$days месяца назад"
            else -> "$days месяцев назад"
        }
        
    }
    return "Something went wrong"
}
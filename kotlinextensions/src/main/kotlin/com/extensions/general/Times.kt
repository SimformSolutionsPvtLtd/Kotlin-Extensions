package com.extensions.general

import com.extensions.Interval
import com.extensions.TimeUnit
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

operator fun Calendar.plus(other : Interval<TimeUnit>) :Calendar {
    val newCalendar = clone() as Calendar
    newCalendar.add(Calendar.MILLISECOND, other.inMilliseconds.longValue.toInt())
    return newCalendar
}

operator fun Calendar.minus(other :Interval<TimeUnit>) :Calendar {
    val newCalendar = clone() as Calendar
    newCalendar.add(Calendar.MILLISECOND, -other.inMilliseconds.longValue.toInt())
    return newCalendar
}

@Suppress("unused")
fun Thread.sleep(interval :Interval<TimeUnit>)
    = Thread.sleep(interval.inMilliseconds.longValue)

@Suppress("unused")
fun Timer.schedule(task :TimerTask, period :Interval<TimeUnit>)
    = schedule(task, period.inMilliseconds.longValue)

@Suppress("unused")
fun Timer.schedule(task :TimerTask, delay :Interval<TimeUnit>, period :Interval<TimeUnit>)
    = schedule(task, delay.inMilliseconds.longValue, period.inMilliseconds.longValue)

@Suppress("unused")
fun Timer.schedule(task :TimerTask, firstTime :Date, period :Interval<TimeUnit>)
    = schedule(task, firstTime, period.inMilliseconds.longValue)

@Suppress("unused")
fun Timer.scheduleAtFixedRate(task :TimerTask, delay :Interval<TimeUnit>, period :Interval<TimeUnit>)
    = scheduleAtFixedRate(task, delay.inMilliseconds.longValue, period.inMilliseconds.longValue)

@Suppress("unused")
fun Timer.scheduleAtFixedRate(task :TimerTask, firstTime :Date, period :Interval<TimeUnit>)
    = scheduleAtFixedRate(task, firstTime, period.inMilliseconds.longValue)

@Suppress("unused")
inline fun Timer.schedule(delay :Interval<TimeUnit>, crossinline action :TimerTask.() -> Unit)
    = schedule(delay.inMilliseconds.longValue, action)

@Suppress("unused")
inline fun Timer.schedule(delay :Interval<TimeUnit>, period :Interval<TimeUnit>, crossinline action :TimerTask.() -> Unit)
    = schedule(delay.inMilliseconds.longValue, period.inMilliseconds.longValue, action)

@Suppress("unused")
inline fun Timer.schedule(time :Date, period :Interval<TimeUnit>, crossinline action :TimerTask.() -> Unit)
    = schedule(time, period.inMilliseconds.longValue, action)

@Suppress("unused")
inline fun Timer.scheduleAtFixedRate(delay :Interval<TimeUnit>, period :Interval<TimeUnit>, crossinline action :TimerTask.() -> Unit)
    = scheduleAtFixedRate(delay.inMilliseconds.longValue, period.inMilliseconds.longValue, action)

@Suppress("unused")
inline fun Timer.scheduleAtFixedRate(time :Date, period :Interval<TimeUnit>, crossinline action :TimerTask.() -> Unit)
    = scheduleAtFixedRate(time, period.inMilliseconds.longValue, action)

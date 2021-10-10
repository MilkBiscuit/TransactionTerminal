package com.cheng.transactionterminal.usecase

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class DateUtilTest {

    private val calendar = Calendar.getInstance()

    @Test
    fun testFormatDate() {
        // 2018-1-1
        calendar.set(2018, Calendar.JANUARY, 1)
        var input = calendar.time
        var result = DateUtil.formatDate(input, "yyyy-MM-dd")
        var expectedString = "2018-01-01"

        // 1970-1-1
        calendar.set(1970, Calendar.JANUARY, 1)
        input = calendar.time
        result = DateUtil.formatDate(input, "yyyy-MM-dd")
        expectedString = "1970-01-01"

        assertEquals(expectedString, result)
    }

    @Test
    fun testToday() {
        val now = Date().time
        val today = DateUtil.getToday()
        val tomorrow = DateUtil.getTomorrow()

        calendar.time = today
        assertTrue(now >= today.time && now < tomorrow.time)
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    fun testIsSameDay() {
        val today = DateUtil.getToday()
        val tomorrow = DateUtil.getTomorrow()
        var result = DateUtil.isSameDay(today, tomorrow)
        assertFalse(result)

        // 2018-1-1, 00:00:00 and 00:00:01
        calendar.set(2018, Calendar.JANUARY, 1, 0, 0, 0)
        var input1 = calendar.time
        calendar.set(2018, Calendar.JANUARY, 1, 0, 0, 1)
        var input2 = calendar.time
        result = DateUtil.isSameDay(input1, input2)
        assertTrue(result)

        // 2018-1-1, 00:00:00 and 23:59:59
        calendar.set(2018, Calendar.JANUARY, 1, 0, 0, 0)
        input1 = calendar.time
        calendar.set(2018, Calendar.JANUARY, 1, 23, 59, 59)
        input2 = calendar.time
        result = DateUtil.isSameDay(input1, input2)
        assertTrue(result)

        // 2018-1-1 23:59:59 and 2018-1-2 00:00:00
        calendar.set(2018, Calendar.JANUARY, 2, 0, 0, 0)
        input1 = calendar.time
        calendar.set(2018, Calendar.JANUARY, 1, 23, 59, 59)
        input2 = calendar.time
        result = DateUtil.isSameDay(input1, input2)
        assertFalse(result)
    }

}

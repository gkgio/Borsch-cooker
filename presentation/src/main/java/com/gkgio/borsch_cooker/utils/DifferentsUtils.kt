package com.gkgio.borsch_cooker.utils

import android.content.Context
import android.content.pm.PackageManager
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.meals.MealsConstants
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.data.HostInterceptor
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * @return dd month yyyy / dd month / СЕГОДНЯ
 */
fun getDateInStandardFormat(
        context: Context,
        date: Date,
        isClassicFormat: Boolean = true,
        withTime: Boolean = false
): String {
    val currentDate = DateTime.now().withTimeAtStartOfDay()

    val currentYear = SimpleDateFormat("yyyy", Locale("ru"))
            .format(currentDate.toDate())
    val checkingYear = DateFormat.format("yyyy", date) as String

    return if (date == currentDate.toDate() && !isClassicFormat) {
        "СЕГОДНЯ"
    } else if (currentYear == checkingYear && !isClassicFormat) {
        if (withTime) {
            dateToUIStringMonthWordAndTime(context, date)
        } else {
            dateToUIStringMonthWord(context, date)
        }
    } else {
        if (withTime) {
            dateToUIStringMonthWordAndYearAndTime(context, date)
        } else {
            dateToUIStringMonthWordAndYear(context, date)
        }
    }
}

/**
 * @return dd month
 */
fun dateToUIStringMonthWord(context: Context, date: Date): String {
    return String.format(
            "%s %s",
            DateFormat.format("dd", date),
            getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String)
    )
}

/**
 * @return dd month - HH:mm
 */
fun dateToUIStringMonthWordAndTime(context: Context, date: Date): String {
    return String.format(
            "%s %s - %s",
            DateFormat.format("dd", date),
            getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
            DateFormat.format("HH:mm", date)
    )
}

/**
 * @return dd month yyyy
 */
fun dateToUIStringMonthWordAndYear(context: Context, date: Date): String {
    return String.format(
            "%s %s %s",
            DateFormat.format("dd", date),
            getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
            DateFormat.format("yyyy", date)
    )
}

/**
 * @return dd month yyyy - HH:mm
 */
fun dateToUIStringMonthWordAndYearAndTime(context: Context, date: Date): String {
    return String.format(
            "%s %s %s - %s",
            DateFormat.format("dd", date),
            getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
            DateFormat.format("yyyy", date),
            DateFormat.format("HH:mm", date)
    )
}

/**
 * @return hh:mm, Day | hh:mm, dd.MM
 */
fun dateToUIStringTimeAndDay(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDate = dateFormat.parse(date)!!
    return String.format(
            "%s, %s",
            DateFormat.format("HH:mm", parsedDate),
            getDayOrDayAndMonthByDate(parsedDate)
    )
}

/**
 * @return dd.MM
 */
fun dateToUIStringDayAndMonth(date: String?): String {
    return if (date != null) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = dateFormat.parse(date)!!
        String.format(
                "%s",
                DateFormat.format("dd.MM", parsedDate),
                getDayOrDayAndMonthByDate(parsedDate)
        )
    } else ""
}

fun getMonthNameByMonthNumber(context: Context, monthNumber: String): String {
    val months = context.resources.getStringArray(R.array.months_s)
    return when (monthNumber) {
        "01" -> months[0].toUpperCase(Locale.getDefault())
        "02" -> months[1].toUpperCase(Locale.getDefault())
        "03" -> months[2].toUpperCase(Locale.getDefault())
        "04" -> months[3].toUpperCase(Locale.getDefault())
        "05" -> months[4].toUpperCase(Locale.getDefault())
        "06" -> months[5].toUpperCase(Locale.getDefault())
        "07" -> months[6].toUpperCase(Locale.getDefault())
        "08" -> months[7].toUpperCase(Locale.getDefault())
        "09" -> months[8].toUpperCase(Locale.getDefault())
        "10" -> months[9].toUpperCase(Locale.getDefault())
        "11" -> months[10].toUpperCase(Locale.getDefault())
        "12" -> months[11].toUpperCase(Locale.getDefault())
        else -> ""
    }
}

fun getDayOrDayAndMonthByDate(date: Date): String {
    return if (DateUtils.isToday(date.time)) {
        "Сегодня"
    } else {
        String.format("%s", DateFormat.format("dd MMM", date))
    }
}

fun getOrdersTypeNameByOrdersType(context: Context, ordersType: String): String {
    return when (ordersType) {
        OrdersConstants.ORDERS_TYPE_ALL -> context.resources.getString(R.string.orders_type_all)
        OrdersConstants.ORDERS_TYPE_ACTIVE -> context.resources.getString(R.string.orders_type_active)
        MealsConstants.MEALS_TYPE_LUNCHES -> context.resources.getString(R.string.meals_lunches)
        MealsConstants.MEALS_TYPE_SINGLES -> context.resources.getString(R.string.meals_singles)
        else -> "unknown"
    }
}

fun getOrdersStatusNameByOrdersStatus(context: Context, ordersStatus: String): String {
    return when (ordersStatus) {
        OrdersConstants.ORDERS_STATUS_CREATED -> context.resources.getString(R.string.orders_status_created)
        OrdersConstants.ORDERS_STATUS_COMPLETED -> context.resources.getString(R.string.orders_status_completed)
        OrdersConstants.ORDERS_STATUS_REJECTED -> context.resources.getString(R.string.orders_status_rejected)
        OrdersConstants.ORDERS_STATUS_CANCELED -> context.resources.getString(R.string.orders_status_canceled)
        OrdersConstants.ORDERS_STATUS_ACCEPTED -> context.resources.getString(R.string.orders_status_accepted)
        OrdersConstants.ORDERS_STATUS_COOKING -> context.resources.getString(R.string.orders_status_cooking)
        OrdersConstants.ORDERS_STATUS_CAN_PICKUP -> context.resources.getString(R.string.orders_status_can_pickup)
        OrdersConstants.ORDERS_STATUS_DELIVERING -> context.resources.getString(R.string.orders_status_delivering)
        else -> context.resources.getString(R.string.orders_status_created)
    }
}

fun getOrderDeliveryUiTypeByType(context: Context, type: String): String =
        if (type == OrdersConstants.ORDERS_TAKE_DELIVERY)
            context.getString(R.string.order_delivery) else context.getString(R.string.order_pickup)

fun convertValueToDecimal(value: String?): String =
        String.format("%.1f", value?.toDouble())

fun getImageUrl(image: String?): String? =
        "http://" + HostInterceptor.CONNECT_URL + ":3001" + image

fun endlessJumpingViewAnimation(view: View) {
    val animation = TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, -0.02f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.02f
    )
    animation.duration = 3000
    animation.repeatCount = -1
    animation.repeatMode = Animation.REVERSE
    animation.interpolator = LinearInterpolator()
    view.startAnimation(animation)
}

fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
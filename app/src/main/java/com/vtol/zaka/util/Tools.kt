package com.vtol.zaka.util

import android.content.Context
import android.widget.Toast
import java.util.Locale

// ── Helper: format number with Arabic-style comma ─────────────────────────────
// produces "1,250"
fun Int.formatArabicNumber(): String =
    String.format(Locale.ENGLISH,"%,d", this)

fun Long.toRelativeTimeArabic(): String {
    val diff = System.currentTimeMillis() - this
    val hours = diff / 3_600_000
    val days  = diff / 86_400_000
    return when {
        hours < 1  -> "منذ قليل"
        hours < 24 -> "منذ $hours ساعتين".takeIf { hours == 2L } ?: "منذ $hours ساعات"
        days  == 1L -> "يوم أمس"
        else       -> "منذ $days أيام"
    }
}

fun Context.showToast(text: String = "Not available yet") {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
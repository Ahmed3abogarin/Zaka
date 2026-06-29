package com.vtol.zaka.domain.models.quiz

import androidx.annotation.DrawableRes
import com.vtol.zaka.R
data class Subject(val label: String, @param:DrawableRes val icon: Int)

val subjects = listOf(
    Subject("علوم", icon = R.drawable.ic_science),
    Subject("لغات", icon = R.drawable.ic_science),
    Subject("رياضيات", icon = R.drawable.ic_science),
    Subject("تاريخ", icon = R.drawable.ic_science),
)

package com.vtol.zaka.util

import android.util.Patterns

object ValidationUtils {

    fun validateEmail(email: String): String? {
        return when {
            email.isEmpty() -> "لا يمكن أن يكون البريد الإلكتروني فارغًا"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "صيغة البريد الإلكتروني غير صحيحة"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isEmpty() -> "لا يمكن أن تكون كلمة المرور فارغة"
            password.length < 8 -> "يجب أن تتكون كلمة المرور من 8 أحرف على الأقل"
            !password.matches(".*[A-Z].*".toRegex()) -> "يجب أن تحتوي كلمة المرور على حرف كبير واحد على الأقل"
            else -> null
        }
    }

    fun validateName(name: String): String? {
        return when {
            name.isEmpty() -> "لا يمكن أن يكون الاسم فارغًا"
            name.length < 2 -> "يجب أن يحتوي الاسم على حرفين على الأقل"
            else -> null
        }
    }

    fun validatePhone(phone: String): String? {
        return when {
            phone.length !in 7..15 -> "رقم الهاتف غير صالح"
            !Patterns.PHONE.matcher(phone).matches() -> "صيغة رقم الهاتف غير صحيحة"
            else -> null
        }
    }
}
package com.vtol.zaka.domain.models.quiz

sealed class ImageValidationError(val messageAr: String) {
    data object TooSmall      : ImageValidationError("الصورة صغيرة جداً، يرجى اختيار صورة أوضح")
    data object TooLarge      : ImageValidationError("حجم الصورة كبير جداً")
    data object LowResolution : ImageValidationError("دقة الصورة منخفضة، حاول تصوير الصفحة بشكل أوضح")
}
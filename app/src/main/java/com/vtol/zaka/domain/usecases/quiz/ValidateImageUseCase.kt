package com.vtol.zaka.domain.usecases.quiz

import android.graphics.Bitmap
import com.vtol.zaka.domain.models.quiz.ImageValidationError
import javax.inject.Inject

class ValidateImageUseCase @Inject constructor() {

    operator fun invoke(bitmap: Bitmap): ImageValidationError? {
        val sizeInBytes  = bitmap.byteCount
        val maxSizeBytes = 10 * 1024 * 1024

        return when {
            bitmap.width < 200 || bitmap.height < 200 -> ImageValidationError.TooSmall
            sizeInBytes > maxSizeBytes                 -> ImageValidationError.TooLarge
            bitmap.width * bitmap.height < 100_000     -> ImageValidationError.LowResolution
            else -> null
        }
    }
}
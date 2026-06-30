package com.vtol.zaka.domain.usecases

import android.graphics.Bitmap
import com.vtol.zaka.domain.repository.QuizRepository
import javax.inject.Inject

class GenerateFromImage @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(bitmap: Bitmap) =
        repository.generateFromImage(bitmap)
}
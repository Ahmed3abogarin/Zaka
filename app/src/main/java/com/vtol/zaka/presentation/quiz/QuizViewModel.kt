package com.vtol.zaka.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtol.zaka.domain.usecases.quiz.GenerateFromPdfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val generateFromPdfUseCase: GenerateFromPdfUseCase
): ViewModel() {

    fun generateFromPdf(pdf: ByteArray){
        viewModelScope.launch {
            generateFromPdfUseCase(pdf)
                .onSuccess {
                    if (it.isNotEmpty()){
                        Log.d("QuizQuestions", it[0].text)
                        Log.d("QuizQuestions", it.size.toString())
                    }
                }.onFailure {
                    Log.d("QuizQuestions", it.message ?: "Error qsdgsg")
                }
        }
    }


}
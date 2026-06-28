package com.vtol.zaka.presentation.quiz

sealed class QuizEvent {
    object NextQuestion: QuizEvent()
    object RevealAnswer: QuizEvent()
    object StartTimer: QuizEvent()
    object RetakeQuiz: QuizEvent()
    data class SelectAnswer(val index: Int): QuizEvent()
}
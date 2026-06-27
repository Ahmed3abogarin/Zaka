package com.vtol.zaka.domain.models.quiz

data class Question(
    val id: String,
    val text: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String,
    val topic: String,
    val difficulty: Difficulty
)

enum class Difficulty(val labelAr: String) {
    EASY("سهل"),
    MEDIUM("متوسط"),
    HARD("صعب")
}
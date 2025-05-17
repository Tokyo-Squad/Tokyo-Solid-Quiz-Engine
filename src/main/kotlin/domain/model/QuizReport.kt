package org.example.domain.model

data class QuizReport(
    val quizTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val finalScore: Int
)
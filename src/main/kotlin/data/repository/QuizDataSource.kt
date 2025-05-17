package org.example.data.repository

import org.example.domain.model.Quiz

interface QuizDataSource {
    fun getQuizById(quizId: String): Quiz?
    fun saveQuiz(quiz: Quiz): String
}
package org.example.data.repository

import org.example.domain.model.Quiz

interface QuizDataSource {
    fun getAllQuizzes(): List<Quiz>
    fun getQuizById(quizId: String): Quiz?
    fun saveQuiz(quiz: Quiz): String
    fun updateQuiz(quiz: Quiz): Boolean
    fun deleteQuiz(quizId: String): Boolean
}
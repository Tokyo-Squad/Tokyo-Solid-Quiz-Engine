package org.example.data.repository

import org.example.data.utils.DataException
import org.example.domain.model.Quiz
import org.example.domain.repository.QuizRepository
import java.util.UUID

class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource,
    private val questionsDataSource: QuestionsDataSource
) : QuizRepository {

    override fun createQuiz(title: String): UUID {
        require(title.isNotBlank()) {
            DataException.ValidationFailed("Quiz don't have a title")
        }
        val id = UUID.randomUUID()
        val quizCreation = Quiz(id = id, title, questionsDataSource.getQuestions())
        quizDataSource.saveQuiz(quizCreation)
        return id
    }

    override fun getQuizById(id: UUID): Quiz {
        return quizDataSource.getQuizById(id.toString())
            ?: throw DataException.NotFound("Quiz $id not found")
    }
}
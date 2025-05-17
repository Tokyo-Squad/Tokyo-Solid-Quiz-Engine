package org.example.data.repository

import org.example.data.utils.DataException
import org.example.domain.model.Quiz
import org.example.domain.repository.QuizRepository
import java.util.UUID

class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource
) : QuizRepository {

    override fun createQuiz(quiz: Quiz) {
        require(quiz.questions.isNotEmpty()) {
            DataException.ValidationError("Quiz must have at least one question")
        }
        quizDataSource.saveQuiz(quiz)
    }

    override fun getQuizById(id: UUID): Quiz {
        return quizDataSource.getQuizById(id.toString())
            ?: throw DataException.NotFound("Quiz $id not found")  // Convert null to exception
    }

    override fun getAllQuizzes(): List<Quiz> {
        require(quizDataSource.getAllQuizzes().isNotEmpty()) { DataException.NotFound("No Quizzes Found") }
        return quizDataSource.getAllQuizzes()
    }

    override fun deleteQuiz(id: UUID): Boolean {
        return quizDataSource.deleteQuiz(id.toString()).also { success ->
            if (!success) {
                throw DataException.NotFound("Quiz with ID $id not found (delete failed)")
            }
        }
    }
}
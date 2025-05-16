package data.local

import org.example.data.repository.QuizDataSource
import org.example.domain.model.Quiz

class InMemoryQuizDataSource(): QuizDataSource {
    override fun getAllQuizzes(): List<Quiz> {
        TODO("Not yet implemented")
    }

    override fun getQuizById(quizId: String): Quiz? {
        TODO("Not yet implemented")
    }

    override fun saveQuiz(quiz: Quiz): String {
        TODO("Not yet implemented")
    }

    override fun updateQuiz(quiz: Quiz): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteQuiz(quizId: String): Boolean {
        TODO("Not yet implemented")
    }

}
package org.example.domain.usecase

import org.example.domain.model.Answer
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.QuizReport
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.model.UserAnswer
import org.example.domain.repository.QuizRepository
import org.example.domain.state.QuizStateManager
import org.example.domain.utils.QuizNotFound
import org.example.domain.utils.QuizUnknownError
import org.example.domain.utils.QuizValidationFailed

class SubmitQuizUseCase(
    private val repository: QuizRepository,
    private val quizStateManger : QuizStateManager
) {
    operator fun invoke(): QuizReport {

        val quizId = quizStateManger.getCurrentQuizId() ?: throw IllegalStateException("No quiz has been started")
        val quiz =  try {
            repository.getQuizById(quizId)
        } catch (e: Exception) {
            throw QuizUnknownError("Error fetching quiz", e)
        }
        val answers = quizStateManger.getAnswers()

        var correctAnswersCount = 0
        for (question in quiz.questions) {
            val userAnswer = answers[question.id]
            if (userAnswer != null) {
                try {
                    when (question) {
                        is MultipleChoiceQuestion -> {
                            if (question.isCorrect(userAnswer.toString())) correctAnswersCount++
                        }
                        is TrueFalseQuestion -> {
                            if (question.isCorrect(userAnswer as Boolean)) correctAnswersCount++
                        }
                    }
                } catch (e: Exception) {
                    throw QuizValidationFailed("Invalid answer for question ${question.id}: ${e.message}")
                }
            }
        }
        val totalQuestions = quiz.questions.size
        val incorrectAnswers = totalQuestions - correctAnswersCount
        val finalScore = correctAnswersCount

        quizStateManger.clearState()

        return QuizReport(
            quizTitle = quiz.title,
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswersCount,
            incorrectAnswers = incorrectAnswers,
            finalScore = finalScore
        )
    }
}

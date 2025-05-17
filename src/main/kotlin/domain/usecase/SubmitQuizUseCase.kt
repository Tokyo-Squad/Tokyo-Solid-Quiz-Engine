package org.example.domain.usecase

import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.QuizReport
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.model.UserAnswer
import org.example.domain.repository.QuizRepository
import org.example.domain.state.QuizStateManager

class SubmitQuizUseCase(
    private val repository: QuizRepository,
    private val quizStateManger : QuizStateManager
) {
    operator fun invoke(userAnswer: UserAnswer): QuizReport {

        if (userAnswer.answers.isEmpty()) {
            return QuizReport(
                quizTitle = "Empty Quiz",
                totalQuestions = 0,
                correctAnswers = 0,
                incorrectAnswers = 0,
                finalScore = 0
            )
        }

        val quizId = userAnswer.quizId
        val quiz = repository.getQuizById(quizId)

        var correctAnswersCount = 0

        for (answer in userAnswer.answers) {
            val question = quiz.questions.find { it.id == answer.questionId }
            if (question != null) {
                when (question) {
                    is MultipleChoiceQuestion -> { if(question.isCorrect(answer)) correctAnswersCount++  }
                    is TrueFalseQuestion -> { if(question.isCorrect(answer)) correctAnswersCount++ }
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

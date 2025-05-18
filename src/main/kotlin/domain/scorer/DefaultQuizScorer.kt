package org.example.domain.scorer

import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import java.util.UUID

class DefaultQuizScorer: QuizScorer {
    override fun calculateScore(
        quiz: Quiz,
        userAnswers: Map<UUID, Any>
    ): Int {
        var correctAnswersCount = 0
        for (question in quiz.questions) {
            val userAnswer = userAnswers[question.id]
            if (userAnswer != null) {
                when (question) {
                    is MultipleChoiceQuestion -> {
                        if (question.isCorrect(userAnswer.toString())) correctAnswersCount++
                    }
                    is TrueFalseQuestion -> {
                        if (question.isCorrect(userAnswer as Boolean)) correctAnswersCount++
                    }
                }
            }
        }
        return correctAnswersCount
    }
}
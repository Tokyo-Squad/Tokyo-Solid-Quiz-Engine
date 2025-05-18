package org.example.domain.scorer

import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.model.UserAnswer
import java.util.UUID

class DefaultQuizScorer: QuizScorer {
    override fun calculateScore(
        quiz: Quiz,
        userAnswers: UserAnswer
    ): Int {
        var correctAnswersCount = 0
        for (question in quiz.questions) {
            val userAnswer =  userAnswers.answers.find { it.questionId == question.id }
            if (userAnswer != null) {
                when (question) {
                    is MultipleChoiceQuestion -> {
                        if (question.isCorrect(userAnswer.selectedOption.toString())) correctAnswersCount++
                    }
                    is TrueFalseQuestion -> {
                        if (question.isCorrect(userAnswer.selectedOption as Boolean)) correctAnswersCount++
                    }
                }
            }
        }
        return correctAnswersCount
    }
}
package org.example.domain.model

import java.util.UUID

abstract class Question<T>(
    val id: UUID,
    val text: String,
    protected val correctAnswer: T
) {
    abstract fun isCorrect(userAnswer: Answer<*>): Boolean
    abstract fun getOptions(): List<T>
}

class MultipleChoiceQuestion(
    id: UUID,
    text: String,
    private val options: List<String>,
    correctAnswer: String
) : Question<String>(id, text, correctAnswer) {
    init {
        require(options.contains(correctAnswer)) {
            "Correct answer must be one of the options"
        }
    }

    override fun isCorrect(userAnswer: Answer<*>): Boolean =
        correctAnswer == userAnswer.selectedOption

    override fun getOptions(): List<String> = options.toList()
}

class TrueFalseQuestion(
    id: UUID,
    text: String,
    correctAnswer: Boolean
) : Question<Boolean>(id, text, correctAnswer) {
    override fun isCorrect(userAnswer: Answer<*>): Boolean =
        correctAnswer == userAnswer.selectedOption

    override fun getOptions(): List<Boolean> = listOf(true, false)
}

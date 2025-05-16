package org.example.domain.model

import java.util.UUID

abstract class Question<T>(
    val id: UUID = UUID.randomUUID(),
    val text: String,
    protected val correctAnswer: T
) {
    abstract fun isCorrect(userAnswer: T): Boolean
    abstract fun getOptions(): List<T>
}

class MultipleChoiceQuestion(
    id: UUID = UUID.randomUUID(),
    text: String,
    private val options: List<String>,
    correctAnswer: String
) : Question<String>(id, text, correctAnswer) {
    init {
        require(options.contains(correctAnswer)) {
            "Correct answer must be one of the options"
        }
    }

    override fun isCorrect(userAnswer: String): Boolean =
        correctAnswer == userAnswer

    override fun getOptions(): List<String> = options.toList()
}

class TrueFalseQuestion(
    id: UUID = UUID.randomUUID(),
    text: String,
    correctAnswer: Boolean
) : Question<Boolean>(id, text, correctAnswer) {
    override fun isCorrect(userAnswer: Boolean): Boolean =
        correctAnswer == userAnswer

    override fun getOptions(): List<Boolean> = listOf(true, false)
}

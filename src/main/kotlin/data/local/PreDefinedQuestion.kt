package org.example.data.inmemory

import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Question
import org.example.domain.model.TrueFalseQuestion
import java.util.UUID

class PreDefinedQuestion {

    private val questions = listOf<Question<*>>(
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "What is the capital of France?",
            options = listOf("Paris", "Rome", "Madrid", "Berlin"),
            correctAnswer = "Paris"
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "Which planet is known as the Red Planet?",
            options = listOf("Earth", "Mars", "Jupiter", "Saturn"),
            correctAnswer = "Mars"
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "Who wrote 'Hamlet'?",
            options = listOf("Shakespeare", "Charles Dickens", "Mark Twain", "Jane Austen"),
            correctAnswer = "Shakespeare"
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "What is the smallest prime number?",
            options = listOf("0", "1", "2", "3"),
            correctAnswer = "2"
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "Which ocean is the largest?",
            options = listOf("Atlantic", "Pacific", "Indian", "Arctic"),
            correctAnswer = "Pacific"
        ),
        TrueFalseQuestion(
            id = UUID.randomUUID(),
            text = "The Earth is flat.",
            correctAnswer = false
        ),
        TrueFalseQuestion(
            id = UUID.randomUUID(),
            text = "There are 24 hours in a day.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = UUID.randomUUID(),
            text = "Java is a type of operating system.",
            correctAnswer = false
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "Which element has the chemical symbol 'O'?",
            options = listOf("Oxygen", "Gold", "Iron", "Hydrogen"),
            correctAnswer = "Oxygen"
        ),
        MultipleChoiceQuestion(
            id = UUID.randomUUID(),
            text = "What is the freezing point of water?",
            options = listOf("0°C", "100°C", "32°C", "-10°C"),
            correctAnswer = "0°C"
        )
    )

    fun getQuestions(): List<Question<*>> = questions

    fun getRandomQuestion(): Question<*> = questions.random()

    fun getQuizById(id: UUID): Question<*> = questions.first { it.id == id }
}
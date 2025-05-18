package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.data.utils.*
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.repository.QuizRepository
import org.example.domain.usecase.CreateQuizUseCase
import org.example.domain.utils.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test

class CreateQuizUseCaseTest {

    private lateinit var quizRepository: QuizRepository
    private lateinit var useCase: CreateQuizUseCase

    @BeforeEach
    fun setUp() {
        quizRepository = mockk()
        useCase = CreateQuizUseCase(quizRepository)
    }

    @Test
    fun `should create quiz successfully`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "Math Quiz",
            questions = listOf(
                TrueFalseQuestion(
                    id = UUID.randomUUID(),
                    text = "2 + 2 = 4",
                    correctAnswer = true
                )
            )
        )
        every { quizRepository.createQuiz(quiz) } returns Unit

        useCase(quiz) // Should not throw
    }

    @Test
    fun `should throw QuizValidationFailed when title is blank`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "",
            questions = listOf(
                TrueFalseQuestion(
                    id = UUID.randomUUID(),
                    text = "Sample?",
                    correctAnswer = true
                )
            )
        )

        val exception = assertThrows<QuizValidationFailed> {
            useCase(quiz)
        }

        assertThat(exception).hasMessageThat().contains("title cannot be blank")
    }

    @Test
    fun `should throw QuizValidationFailed when no questions`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "Empty Quiz",
            questions = emptyList()
        )

        val exception = assertThrows<QuizValidationFailed> {
            useCase(quiz)
        }

        assertThat(exception).hasMessageThat().contains("at least one question")
    }

    @Test
    fun `should throw QuizCreationFailed when StorageError is thrown`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "Failing Quiz",
            questions = listOf(
                TrueFalseQuestion(
                    id = UUID.randomUUID(),
                    text = "Question?",
                    correctAnswer = false
                )
            )
        )
        val storageError = StorageError("Failed to save", Exception("Disk full"))
        every { quizRepository.createQuiz(quiz) } throws storageError

        val exception = assertThrows<QuizCreationFailed> {
            useCase(quiz)
        }

        assertThat(exception).hasMessageThat().contains("Could not create quiz")
        assertThat(exception).hasCauseThat().isEqualTo(storageError)
    }

    @Test
    fun `should throw QuizValidationFailed when ValidationError is thrown by repo`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "Invalid",
            questions = listOf(
                TrueFalseQuestion(
                    id = UUID.randomUUID(),
                    text = "Yes?",
                    correctAnswer = true
                )
            )
        )
        every { quizRepository.createQuiz(quiz) } throws ValidationError("Invalid quiz structure")

        val exception = assertThrows<QuizValidationFailed> {
            useCase(quiz)
        }

        assertThat(exception).hasMessageThat().contains("Invalid quiz data")
    }

    @Test
    fun `should throw QuizUnknownError when Unknown is thrown`() {
        val quiz = Quiz(
            id = UUID.randomUUID(),
            title = "Mystery Quiz",
            questions = listOf(
                TrueFalseQuestion(
                    id = UUID.randomUUID(),
                    text = "Unknown?",
                    correctAnswer = true
                )
            )
        )
        val innerCause = Exception("Database crashed")
        val unknown = Unknown("Unknown error", innerCause)
        every { quizRepository.createQuiz(quiz) } throws unknown

        val exception = assertThrows<QuizUnknownError> {
            useCase(quiz)
        }

        assertThat(exception).hasMessageThat().contains("Unexpected error while creating quiz")
        assertThat(exception).hasCauseThat().isEqualTo(unknown)
        assertThat(exception.cause?.cause).isEqualTo(innerCause)
    }
}
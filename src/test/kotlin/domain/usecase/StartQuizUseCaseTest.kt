package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.data.utils.*
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Question
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.repository.QuizRepository
import org.example.domain.usecase.StartQuizUseCase
import org.example.domain.utils.QuizNotFound
import org.example.domain.utils.QuizStorageFailed
import org.example.domain.utils.QuizUnknownError
import org.example.domain.utils.QuizValidationFailed
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test

class StartQuizUseCaseTest {

    private lateinit var quizRepository: QuizRepository
    private lateinit var useCase: StartQuizUseCase

    @BeforeEach
    fun setUp() {
        quizRepository = mockk()
        useCase = StartQuizUseCase(quizRepository)
    }

    @Test
    fun `should return questions when quiz exists`() {
        // Given
        val quizId = UUID.randomUUID()
        val questions: List<Question<*>> = listOf(
            MultipleChoiceQuestion(
                id = UUID.randomUUID(),
                text = "What is the capital of France?",
                options = listOf("Paris", "London", "Berlin"),
                correctAnswer = "Paris"
            ), TrueFalseQuestion(
                id = UUID.randomUUID(), text = "Is the Earth flat?", correctAnswer = false
            )
        )
        val quiz = Quiz(id = quizId, title = "Geography Quiz", questions = questions)
        every { quizRepository.getQuizById(quizId) } returns quiz

        // When
        val result = useCase(quizId)

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].text).isEqualTo("What is the capital of France?")
        assertThat(result[1].text).isEqualTo("Is the Earth flat?")
    }

    @Test
    fun `should throw QuizNotFound when NotFound exception is thrown by repository`() {
        // Given
        val quizId = UUID.randomUUID()
        every { quizRepository.getQuizById(quizId) } throws NotFound("Not found")

        // When
        val exception = assertThrows<QuizNotFound> {
            useCase(quizId)
        }
        //  Then
        assertThat(exception).hasMessageThat().contains("Quiz with ID")
        assertThat(exception).hasMessageThat().contains(quizId.toString())
    }

    @Test
    fun `should throw QuizValidationFailed when quizId is invalid`() {
        // Given
        val invalidQuizId = UUID(0, 0)

        // When
        val exception = assertThrows<QuizValidationFailed> {
            useCase(invalidQuizId)
        }

        // Then
        assertThat(exception).hasMessageThat().contains("Quiz ID cannot be blank or invalid")

    }

    @Test
    fun `should throw QuizStorageFailed when StorageError is thrown by repository`() {
        // Given
        val quizId = UUID.randomUUID()
        val cause = Exception("Disk error")
        every { quizRepository.getQuizById(quizId) } throws StorageError("Storage failure", cause)

        // When
        val exception = assertThrows<QuizStorageFailed> {
            useCase(quizId)
        }
        //  Then

        assertThat(exception).hasMessageThat().contains("Could not retrieve quiz")
        assertThat(exception).hasCauseThat().isEqualTo(cause)
    }

    @Test
    fun `should throw QuizUnknownError when other DataException is thrown by repository`() {
        // Given
        val quizId = UUID.randomUUID()
        val cause = Exception("Unknown failure")
        every { quizRepository.getQuizById(quizId) } throws Unknown("Unknown error", cause)

        // When
        val exception = assertThrows<QuizUnknownError> {
            useCase(quizId)
        }
        //  Then

        assertThat(exception).hasMessageThat().contains("Unexpected error for quiz")
        assertThat(exception).hasCauseThat().isEqualTo(cause)
    }
}
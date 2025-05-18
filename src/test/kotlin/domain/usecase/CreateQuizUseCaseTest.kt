package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.data.utils.*
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
        val title = "Math Quiz"
        val quizId = UUID.randomUUID()
        every { quizRepository.createQuiz(title) } returns quizId

        val result = useCase(title)

        assertThat(result).isEqualTo(quizId)
    }

    @Test
    fun `should throw QuizValidationFailed when title is blank`() {
        val title = ""

        val exception = assertThrows<QuizValidationFailed> {
            useCase(title)
        }

        assertThat(exception).hasMessageThat().contains("title cannot be blank")
    }

    @Test
    fun `should throw QuizCreationFailed when StorageError is thrown`() {
        val title = "Failing Quiz"
        val storageError = StorageError("Failed to save", Exception("Disk full"))
        every { quizRepository.createQuiz(title) } throws storageError

        val exception = assertThrows<QuizCreationFailed> {
            useCase(title)
        }

        assertThat(exception).hasMessageThat().contains("Could not create quiz")
        assertThat(exception).hasCauseThat().isEqualTo(storageError)
    }

    @Test
    fun `should throw QuizValidationFailed when ValidationError is thrown`() {
        val title = "Invalid"
        every { quizRepository.createQuiz(title) } throws ValidationError("Invalid quiz structure")

        val exception = assertThrows<QuizValidationFailed> {
            useCase(title)
        }

        assertThat(exception).hasMessageThat().contains("Invalid quiz data")
    }

    @Test
    fun `should throw QuizUnknownError when Unknown is thrown`() {
        val title = "Mystery Quiz"
        val innerCause = Exception("Database crashed")
        val unknown = Unknown("Unknown error", innerCause)
        every { quizRepository.createQuiz(title) } throws unknown

        val exception = assertThrows<QuizUnknownError> {
            useCase(title)
        }

        assertThat(exception).hasMessageThat().contains("Unexpected error while creating quiz")
        assertThat(exception).hasCauseThat().isEqualTo(unknown)
        assertThat(exception.cause?.cause).isEqualTo(innerCause)
    }
}
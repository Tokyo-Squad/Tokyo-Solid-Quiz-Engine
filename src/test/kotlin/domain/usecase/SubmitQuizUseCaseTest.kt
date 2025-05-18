package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.repository.QuizRepository
import org.example.domain.state.QuizStateManager
import org.example.domain.usecase.SubmitQuizUseCase
import org.example.domain.utils.QuizUnknownError
import org.example.domain.utils.QuizValidationFailed
import org.junit.jupiter.api.BeforeEach
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SubmitQuizUseCaseTest {


    private var repository : QuizRepository = mockk(relaxed = true)
    private var stateManager : QuizStateManager = mockk(relaxed = true)
    private lateinit var submitQuizUseCase : SubmitQuizUseCase


    @BeforeEach
    fun setUp() {
        submitQuizUseCase = SubmitQuizUseCase(repository,stateManager)
    }

    @Test
    fun `invoke should calculate correct score and return QuizReport`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()
        val question3Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "What is the capital of France?", listOf("London", "Paris", "Berlin"), "Paris")
        val question2 = TrueFalseQuestion(question2Id, "The Earth is flat.", false)
        val question3 = MultipleChoiceQuestion(question3Id, "What is 2+2?", listOf("3", "4", "5"), "4")

        val questions = listOf(question1, question2, question3)
        val quizId = UUID.randomUUID()
        val quiz = Quiz(quizId, "Test Quiz", questions)


        val userAnswers: Map<UUID, Any> = mapOf(
            question1Id to "Paris",
            question2Id to false,
            question3Id to "5"
        )

        every { repository.getQuizById(quizId) } returns quiz

        every { stateManager.getCurrentQuizId() } returns quizId
        every { stateManager.getAnswers() } returns userAnswers


        // When
        val report = submitQuizUseCase.invoke()

        // Then
        assertThat(report.quizTitle).isEqualTo("Test Quiz")
        assertThat(report.totalQuestions).isEqualTo(3)
        assertThat(report.correctAnswers).isEqualTo(2)
        assertThat(report.incorrectAnswers).isEqualTo(1)
        assertThat(report.finalScore).isEqualTo(2)
    }

    @Test
    fun `invoke should throw IllegalStateException if no quiz has been started`() {
        // Given
        every { stateManager.getCurrentQuizId() } returns null

        // When & Then
        assertFailsWith<IllegalStateException> { submitQuizUseCase.invoke() }
    }

    @Test
    fun `invoke should throw QuizUnknownError if repository throws an exception`() {
        // Given
        val quizId = UUID.randomUUID()
        every { stateManager.getCurrentQuizId() } returns quizId
        every { repository.getQuizById(quizId) } throws Exception("Database error")

        // When & Then
        assertFailsWith<QuizUnknownError> { submitQuizUseCase.invoke() }
            .also { assertThat(it.message).contains("Error fetching quiz") }
    }

    @Test
    fun `invoke should handle empty answers gracefully`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "Question 1", listOf("A", "B"), "A")
        val question2 = TrueFalseQuestion(question2Id, "Question 2", true)

        val questions = listOf(question1, question2)
        val quizId = UUID.randomUUID()
        val quiz = Quiz(quizId, "Empty Answers Quiz", questions)

        every { stateManager.getCurrentQuizId() } returns quizId
        every { repository.getQuizById(quizId) } returns quiz
        every { stateManager.getAnswers() } returns emptyMap()
        every { stateManager.clearState() } returns Unit

        // When
        val report = submitQuizUseCase.invoke()

        // Then
        assertThat(report.quizTitle).isEqualTo("Empty Answers Quiz")
        assertThat(report.totalQuestions).isEqualTo(2)
        assertThat(report.correctAnswers).isEqualTo(0)
        assertThat(report.incorrectAnswers).isEqualTo(2)
        assertThat(report.finalScore).isEqualTo(0)
    }

    @Test
    fun `invoke should throw QuizValidationFailed for invalid true false answer`() {
        // Given
        val questionId = UUID.randomUUID()
        val question = TrueFalseQuestion(questionId, "Question 1", true)
        val quizId = UUID.randomUUID()
        val quiz = Quiz(quizId, "Validation Quiz", listOf(question))
        val userAnswers: Map<UUID, Any> = mapOf(questionId to "yes") // Invalid answer type

        every { stateManager.getCurrentQuizId() } returns quizId
        every { repository.getQuizById(quizId) } returns quiz
        every { stateManager.getAnswers() } returns userAnswers

        // When & Then
        assertFailsWith<QuizValidationFailed> { submitQuizUseCase.invoke() }
            .also { assertThat(it.message).contains("Invalid answer for question $questionId") }
    }
}
package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.domain.model.Answer
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.model.UserAnswer
import org.example.domain.repository.QuizRepository
import org.example.domain.state.QuizStateManager
import org.example.domain.usecase.SubmitQuizUseCase
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import java.util.UUID
import kotlin.test.Test

class SubmitQuizUseCaseTest {


    private var repository : QuizRepository = mockk(relaxed = true)
    private var stateManger : QuizStateManager = mockk(relaxed = true)
    private lateinit var submitQuizUseCas : SubmitQuizUseCase


    @BeforeEach
    fun setUp() {
        submitQuizUseCas = SubmitQuizUseCase(repository,stateManger)
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

        val userAnswer = UserAnswer(
            quizId = quizId,
            answers = listOf(
                Answer(question1Id,"Paris"), // Correct
                Answer(question2Id,false), // Correct
                Answer(question3Id,"5") // Incorrect
            )
        )
        every { repository.getQuizById(quizId) } returns quiz

        // When
        val report = submitQuizUseCas.invoke(userAnswer)

        // Then
        assertThat(report.quizTitle).isEqualTo("Test Quiz")
        assertThat(report.totalQuestions).isEqualTo(3)
        assertThat(report.correctAnswers).isEqualTo(2)
        assertThat(report.incorrectAnswers).isEqualTo(1)
        assertThat(report.finalScore).isEqualTo(2)
    }
}
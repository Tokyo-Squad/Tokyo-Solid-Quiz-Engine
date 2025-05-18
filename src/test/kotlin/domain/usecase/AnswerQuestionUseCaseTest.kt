package domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.domain.utils.AnswerValidationFailed
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import com.google.common.truth.Truth.assertThat
import org.example.data.utils.Unknown
import org.example.domain.model.Answer
import org.example.domain.state.QuizStateManager
import org.example.domain.usecase.AnswerQuestionUseCase

class AnswerQuestionUseCaseTest {

    private lateinit var quizStateManager: QuizStateManager
    private lateinit var useCase: AnswerQuestionUseCase

    @BeforeEach
    fun setup() {
        quizStateManager = mockk(relaxed = true)
        useCase = AnswerQuestionUseCase(quizStateManager)
    }

    @Test
    fun `should add answer successfully when answer is valid`() {
        val answer = Answer(
            questionId = UUID.randomUUID(),
            selectedOption = "Option A"
        )

        useCase(answer)

        verify { quizStateManager.addAnswer(answer.questionId, answer.selectedOption) }
    }

    @Test
    fun `should throw AnswerValidationFailed when questionId is blank`() {
        val blankQuestionId = UUID.fromString("00000000-0000-0000-0000-000000000000")
        val answer = Answer<String>(
            questionId = blankQuestionId,
            selectedOption = "Some answer"
        )

        val exception = assertThrows<AnswerValidationFailed> {
            useCase(answer)
        }

        assertThat(exception).hasMessageThat().contains("Question ID cannot be blank")
    }

    @Test
    fun `should rethrow AnswerValidationFailed as is`() {
        val answer = Answer(UUID.randomUUID(), "Answer")
        val exception = AnswerValidationFailed("Invalid answer")

        every { quizStateManager.addAnswer(answer.questionId, answer.selectedOption) } throws exception

        val thrown = assertThrows<AnswerValidationFailed> {
            useCase(answer)
        }

        assertThat(thrown).isEqualTo(exception)
    }

    @Test
    fun `should throw Unknown when unexpected exception is thrown`() {
        val answer = Answer(UUID.randomUUID(), "Answer")
        val unexpectedException = RuntimeException("Unexpected error")

        every { quizStateManager.addAnswer(answer.questionId, answer.selectedOption) } throws unexpectedException

        val thrown = assertThrows<Unknown> {
            useCase(answer)
        }

        assertThat(thrown).hasMessageThat().contains("Unexpected error while answering question")
        assertThat(thrown.cause).isEqualTo(unexpectedException)
    }
}

package domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.example.data.utils.NotFound
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Question
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.repository.QuizRepository
import org.example.domain.usecase.StartQuizUseCase
import org.example.domain.utils.QuizNotFound
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
   ),
   TrueFalseQuestion(
    id = UUID.randomUUID(),
    text = "Is the Earth flat?",
    correctAnswer = false
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
 fun `should throw QuizNotFound when quiz does not exist`() {
  // Given
  val quizId = UUID.randomUUID()
  every { quizRepository.getQuizById(quizId) } throws NotFound("Quiz with ID $quizId not found")

  // When + Then
  val exception = assertThrows<QuizNotFound> {
   useCase(quizId)
  }

  assertThat(exception).hasMessageThat().contains("Quiz with ID")
  assertThat(exception).hasMessageThat().contains("not found")
 }
}
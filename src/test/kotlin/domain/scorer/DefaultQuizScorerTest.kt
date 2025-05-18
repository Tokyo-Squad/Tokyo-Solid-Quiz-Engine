package domain.scorer

import com.google.common.truth.Truth.assertThat
import org.example.domain.model.MultipleChoiceQuestion
import org.example.domain.model.Quiz
import org.example.domain.model.TrueFalseQuestion
import org.example.domain.scorer.DefaultQuizScorer
import java.util.UUID
import kotlin.test.Test

class DefaultQuizScorerTest {

    private val quizScorer = DefaultQuizScorer()

    @Test
    fun `calculateScore should return the correct score for a mix of question types`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()
        val question3Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "MCQ 1", listOf("A", "B"), "A")
        val question2 = TrueFalseQuestion(question2Id, "TF 1", true)
        val question3 = MultipleChoiceQuestion(question3Id, "MCQ 2", listOf("X", "Y"), "Y")

        val questions = listOf(question1, question2, question3)
        val quiz = Quiz(UUID.randomUUID(), "Test Quiz", questions)

        val userAnswers = mapOf(
            question1Id to "A", // Correct
            question2Id to false, // Incorrect
            question3Id to "Y"  // Correct
        )

        // When
        val score = quizScorer.calculateScore(quiz, userAnswers)

        // Then
        assertThat(score).isEqualTo(2)
    }

    @Test
    fun `calculateScore should return 0 if no answers are correct`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "MCQ 1", listOf("A", "B"), "A")
        val question2 = TrueFalseQuestion(question2Id, "TF 1", true)

        val questions = listOf(question1, question2)
        val quiz = Quiz(UUID.randomUUID(), "Test Quiz", questions)

        val userAnswers = mapOf(
            question1Id to "B", // Incorrect
            question2Id to false // Incorrect
        )

        // When
        val score = quizScorer.calculateScore(quiz, userAnswers)

        // Then
        assertThat(score).isEqualTo(0)
    }

    @Test
    fun `calculateScore should handle empty user answers`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "MCQ 1", listOf("A", "B"), "A")
        val question2 = TrueFalseQuestion(question2Id, "TF 1", true)

        val questions = listOf(question1, question2)
        val quiz = Quiz(UUID.randomUUID(), "Test Quiz", questions)

        val userAnswers = emptyMap<UUID, Any>()

        // When
        val score = quizScorer.calculateScore(quiz, userAnswers)

        // Then
        assertThat(score).isEqualTo(0)
    }

    @Test
    fun `calculateScore should handle questions with no corresponding user answer`() {
        // Given
        val question1Id = UUID.randomUUID()
        val question2Id = UUID.randomUUID()
        val question3Id = UUID.randomUUID()

        val question1 = MultipleChoiceQuestion(question1Id, "MCQ 1", listOf("A", "B"), "A")
        val question2 = TrueFalseQuestion(question2Id, "TF 1", true)

        val questions = listOf(question1, question2, MultipleChoiceQuestion(question3Id, "MCQ 3", listOf("X", "Y"), "X"))
        val quiz = Quiz(UUID.randomUUID(), "Test Quiz", questions)

        val userAnswers = mapOf(
            question1Id to "A", // Correct
            question2Id to true  // Correct
            // Missing answer for question3
        )

        // When
        val score = quizScorer.calculateScore(quiz, userAnswers)

        // Then
        assertThat(score).isEqualTo(2)
    }

}
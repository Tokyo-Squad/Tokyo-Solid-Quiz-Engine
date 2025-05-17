package org.example.domain.model

import java.util.UUID

data class UserAnswer(
    val quizId: UUID,
    val answers: List<Answer<*>>
)
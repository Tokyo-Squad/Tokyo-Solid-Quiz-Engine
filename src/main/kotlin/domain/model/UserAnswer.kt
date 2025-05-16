package org.example.domain.model

import java.util.UUID

data class UserAnswer(
    val quizId: UUID = UUID.randomUUID(),
    val answers: Map<String, String>
)
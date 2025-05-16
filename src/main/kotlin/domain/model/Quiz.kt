package org.example.domain.model

import java.util.UUID

data class Quiz(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val questions: List<Question<*>>
)
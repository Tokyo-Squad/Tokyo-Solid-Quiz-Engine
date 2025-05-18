package org.example.domain.model

import java.util.UUID

data class Quiz(
    val id: UUID,
    val title: String,
    val questions: List<Question<*>>
)
package org.example.domain.model

import java.util.UUID

data class Answer<T>(
    val questionId: UUID = UUID.randomUUID(),
    val selectedOption: T
)
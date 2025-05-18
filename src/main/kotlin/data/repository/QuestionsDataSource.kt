package org.example.data.repository

import org.example.domain.model.Question

interface QuestionsDataSource {
    fun getQuestions(): List<Question<*>>
}
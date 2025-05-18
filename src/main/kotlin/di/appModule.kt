package org.example.di

import org.example.domain.state.InMemoryQuizStateManager
import org.example.domain.state.QuizStateManager
import org.koin.dsl.module

val appModule = module {
    single<QuizStateManager> { InMemoryQuizStateManager() }
}
package com.example.appquizlizard.ui.theme.navigation

import androidx.compose.runtime.compositionLocalOf
import com.example.appquizlizard.backend.repositories.CategoryRepository
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.UserRepository

val LocalCategoryRepository = compositionLocalOf<CategoryRepository> {
    error("No CategoryRepository provided")
}

val LocalQuestionRepository = compositionLocalOf<QuestionRepository> {
    error("No QuestionRepository provided")
}

val LocalUserRepository = compositionLocalOf<UserRepository> {
    error("No UserRepository provided")
}
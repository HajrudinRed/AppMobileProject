package com.example.appquizlizard.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appquizlizard.backend.repositories.CategoryRepository
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.UserRepository
import com.example.appquizlizard.backend.viewmodel.AuthViewModel
import com.example.appquizlizard.ui.theme.screens.*
import javax.inject.Inject

@Composable
fun AppNavHost(
    categoryRepository: CategoryRepository,
    questionRepository: QuestionRepository,
    userRepository: UserRepository
) {
    val navController = rememberNavController()
    val authViewModel = remember { AuthViewModel() }

    CompositionLocalProvider(
        LocalCategoryRepository provides categoryRepository,
        LocalQuestionRepository provides questionRepository,
        LocalUserRepository provides userRepository
    ) {
        NavHost(
            navController = navController,
            startDestination = "intro_screen"
        ) {
            composable("intro_screen") {
                IntroScreen(
                    navigateToLogin = {
                        navController.navigate("login_screen")
                    },
                    navigateToSignUp = {
                        navController.navigate("signup_screen")
                    }
                )
            }

            composable("login_screen") {
                LoginScreen(
                    authViewModel = authViewModel,
                    navigateToMainScreen = { userId ->
                        navController.navigate("main_screen") {
                            popUpTo("intro_screen") { inclusive = true }
                        }
                    },
                    navigateToSignUp = {
                        navController.navigate("signup_screen")
                    }
                )
            }

            composable("signup_screen") {
                SignUpScreen(
                    authViewModel = authViewModel,
                    navigateToMainScreen = { userId ->
                        navController.navigate("main_screen") {
                            popUpTo("intro_screen") { inclusive = true }
                        }
                    },
                    navigateToLogin = {
                        navController.navigate("login_screen")
                    }
                )
            }

            composable("main_screen") {
                MainScreen(
                    categoryRepository = categoryRepository,
                    navigateToCategory = { categoryId ->
                        navController.navigate("quiz_screen/$categoryId")
                    }
                )
            }

            composable(
                "quiz_screen/{categoryId}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 1
                QuizScreen(
                    categoryId = categoryId,
                    navigateToFinish = { catId, score ->
                        navController.navigate("finish_screen/$catId/$score") {
                            popUpTo("quiz_screen/{categoryId}") { inclusive = true }
                        }
                    }
                )
            }

            composable(
                "finish_screen/{categoryId}/{score}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.IntType },
                    navArgument("score") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 1
                val score = backStackEntry.arguments?.getInt("score") ?: 0

                FinishScreen(
                    categoryId = categoryId,
                    score = score,
                    navigateToMainScreen = {
                        navController.navigate("main_screen") {
                            popUpTo("main_screen") { inclusive = true }
                        }
                    },
                    playAgain = { catId ->
                        navController.navigate("quiz_screen/$catId") {
                            popUpTo("finish_screen/{categoryId}/{score}") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
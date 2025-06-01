package com.example.appquizlizard.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appquizlizard.R
import com.example.appquizlizard.backend.model.Category
import com.example.appquizlizard.backend.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen(
    categoryRepository: CategoryRepository? = null,
    navigateToCategory: (Int) -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFF"))
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load categories from database
    LaunchedEffect(key1 = categoryRepository) {
        if (categoryRepository != null) {
            try {
                withContext(Dispatchers.IO) {
                    categories = categoryRepository.getAllCategories()
                }
                isLoading = false
            } catch (e: Exception) {
                error = e.message
                isLoading = false
            }
        } else {
            // Preview mode - use sample data
            categories = listOf(
                Category(1, "Countries"),
                Category(2, "History"),
                Category(3, "Science")
            )
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top AppBar-style header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF800020)), // Burgundy
            contentAlignment = Alignment.Center
        ) {
            Text("QuizLizard", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // Main Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 20.dp)
        ) {
            // Logo image
            Image(
                painter = painterResource(R.drawable.quizlizardlogo),
                contentDescription = "QuizLizard Logo",
                modifier = Modifier.size(225.dp)
            )

            Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            when {
                isLoading -> {
                    CircularProgressIndicator(color = Color(0xFF800020))
                }
                error != null -> {
                    Text(
                        "Error loading categories: $error",
                        color = Color.Red
                    )
                }
                categories.isEmpty() -> {
                    Text("No categories available", color = Color.Gray)
                }
                else -> {
                    // Show categories in a scrollable column
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            Button(
                                onClick = { navigateToCategory(category.categoryId) },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020))
                            ) {
                                Text(text = category.name, color = Color.White, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

        // Bottom Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF800020)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_user_icon),
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_user_icon),
                contentDescription = "Categories",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navigateToCategory = {})
}
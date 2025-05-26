package com.example.appquizlizard.ui.theme.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.appquizlizard.R
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource


@Composable
fun MainScreen(navigateToCategory: (String) -> Unit) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFFFF"))
    val shape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 50.dp,
        bottomEnd = 50.dp

    )
    Column(
        modifier = Modifier
            .padding(0.dp)
            .background(color = backgroundColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
                .height(650.dp)
                .padding(vertical = 20.dp)
        ) {
            // Logo image
            Image(
                painter = painterResource(R.drawable.quizlizardlogo), // Add your image to drawable
                contentDescription = "QuizLizard Logo",
                modifier = Modifier.size(225.dp)
            )

            Text("Gategories", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            // Category buttons
            val categories = listOf("Countries", "Math", "Random", "History")
            categories.forEach { category ->
                Button(
                    onClick = { navigateToCategory(category) },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020))
                ) {
                    Text(text = category, color = Color.White, fontSize = 16.sp)
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
                painter = painterResource(R.drawable.ic_user_icon), // Replace with actual icons
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_user_icon), // Replace with actual icons
                contentDescription = "Gategories",
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


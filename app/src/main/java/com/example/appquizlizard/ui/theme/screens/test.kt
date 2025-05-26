package com.example.appquizlizard.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun Intro2ScreenPreview(){
    Intro2Screen()
}


@Composable
fun Intro2Screen () {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFFFF"))


    val shape = RoundedCornerShape(
        topStart = CornerSize(0.dp), // Top-left corner
        topEnd = CornerSize(0.dp),   // Top-right corner
        bottomStart = CornerSize(50.dp), // Bottom-left corner
        bottomEnd = CornerSize(50.dp)    // Bottom-right corner
    )


    //Column
    Column(
        modifier = Modifier
            .padding(0.dp)
            .background(color = backgroundColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Box
        // Add your content here
    }
}
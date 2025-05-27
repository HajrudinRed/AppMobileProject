package com.example.appquizlizard.backend.model

import android.graphics.Picture
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["questionId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["categoryId"])]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int = 0,
    val categoryId: Int,
    val questionPictureResId: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

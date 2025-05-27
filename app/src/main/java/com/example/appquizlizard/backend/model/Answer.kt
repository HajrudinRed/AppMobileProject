package com.example.appquizlizard.backend.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["questionId"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val answerId: Int = 0,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)

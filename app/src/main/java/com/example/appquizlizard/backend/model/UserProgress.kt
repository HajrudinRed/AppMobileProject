package com.example.appquizlizard.backend.model

import kotlinx.serialization.Serializable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Serializable
@Entity(tableName = "progress",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["progressId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Question::class,
        parentColumns = ["progressId"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class UserProgress(
    @PrimaryKey(autoGenerate = true)
    val progressId: Int = 0,
    val userId: Int,
    val questionId: Int,
    val isCorrect: Boolean,
    val timestamp: Long
)

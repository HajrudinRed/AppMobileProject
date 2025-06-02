package com.example.appquizlizard.backend.model

import kotlinx.serialization.Serializable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Serializable
@Entity(tableName = "progress",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Question::class,
        parentColumns = ["questionId"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Category::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )
                  ],
    indices = [Index(value = ["userId"]),Index(value = ["questionId"])],

)

data class UserProgress(
    @PrimaryKey(autoGenerate = true)
    val progressId: Int = 0,
    val userId: Int,
    val categoryId: Int,
    val score: Int = 0,
    val questionId: Int,
    val answerId: Int,
    val isCorrect: Boolean,
    val timestamp: Long
)

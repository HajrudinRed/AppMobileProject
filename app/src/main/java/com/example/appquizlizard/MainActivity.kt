package com.example.appquizlizard

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appquizlizard.backend.dao.AnswerDao
import com.example.appquizlizard.backend.dao.CategoryDao
import com.example.appquizlizard.backend.dao.QuestionDao
import com.example.appquizlizard.backend.database.AppDatabase
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Category
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.model.User
import com.example.appquizlizard.ui.theme.theme.AppQuizLizardTheme
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppQuizLizardTheme {


                }
            }
        }
    }
@HiltAndroidApp
class DatabaseSeed: Application() {
    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            /*try {
                val db = database
                Log.d("DatabaseSeeding", "Starting database seeding")
                val testUser = User(userId = 0, username = "testuser", email = "test@example.com", password = "password123")
                val userId = db.userDao().insert(testUser) // userId is Long
                val user1 = db.userDao().getUserById(userId.toInt())
                // Seed categories and get their IDs
                val countriesCategoryId = db.categoryDao().insert(Category(name = "Countries")).toInt()
                val randomCategoryId = db.categoryDao().insert(Category(name = "Random")).toInt()
                val historyCategoryId = db.categoryDao().insert(Category(name = "History")).toInt()

                seedHistoryQuestions(db.questionDao(), db.answerDao(), historyCategoryId)
                seedFlagQuestions(db.questionDao(), db.answerDao(), countriesCategoryId)
                seedGeneralQuestions(db.questionDao(), db.answerDao(), randomCategoryId)
                Log.d("DatabaseSeeding", "Database seeding completed")
            } catch (e: Exception) {
                Log.e("DatabaseSeeding", "Error seeding database", e)
            }*/
        }
    }

}

fun seedCategories(categoryDao: CategoryDao) {
    CoroutineScope(Dispatchers.IO).launch {
        categoryDao.insert(Category(name = "Countries"))
        categoryDao.insert(Category(name = "Math"))
        categoryDao.insert(Category(name = "Random"))
        categoryDao.insert(Category(name = "History"))
    }
}

fun seedHistoryQuestions(questionDao: QuestionDao, answerDao: AnswerDao, historyCategoryId: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        // 1. Great Wall
        val q1 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.great_wall,
            questionText = "Which of the following civilizations is known for building the Great Wall?"
        )
        val q1Id = questionDao.insert(q1) // If insert returns rowId, otherwise get inserted questionId
        answerDao.insert(Answer(questionId = q1Id.toInt(), text = "China", isCorrect = true))
        answerDao.insert(Answer(questionId = q1Id.toInt(), text = "Egypt", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id.toInt(), text = "Greece", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id.toInt(), text = "Rome", isCorrect = false))

        // 2. First US President
        val q2 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.george_washington,
            questionText = "Who was the first President of the United States?"
        )
        val q2Id = questionDao.insert(q2)
        answerDao.insert(Answer(questionId = q2Id.toInt(), text = "George Washington", isCorrect = true))
        answerDao.insert(Answer(questionId = q2Id.toInt(), text = "Thomas Jefferson", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id.toInt(), text = "Abraham Lincoln", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id.toInt(), text = "John Adams", isCorrect = false))

        // Repeat for other questions...
        // 3. Last of 5 good emperors of Rome
        val q3 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.marcus_aurelius,
            questionText = "Who was the last of the 5 good emperors of Rome?"
        )
        val q3Id = questionDao.insert(q3)
        answerDao.insert(Answer(questionId = q3Id.toInt(), text = "Julius Caesar", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id.toInt(), text = "Marcus Aurelius", isCorrect = true))
        answerDao.insert(Answer(questionId = q3Id.toInt(), text = "Nero", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id.toInt(), text = "Constantine", isCorrect = false))

        // 4. Pyramids of Giza
        val q4 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.pyramids_of_giza,
            questionText = "Which ancient civilization built the iconic Pyramids of Giza?"
        )
        val q4Id = questionDao.insert(q4)
        answerDao.insert(Answer(questionId = q4Id.toInt(), text = "Ancient Egypt", isCorrect = true))
        answerDao.insert(Answer(questionId = q4Id.toInt(), text = "Ancient Greece", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id.toInt(), text = "Mesopotamia", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id.toInt(), text = "Persia", isCorrect = false))

        // 5. Mongol Empire founder
        val q5 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.gengis_khan,
            questionText = "Who was the founder of the Mongol Empire, one of the largest empires in history?"
        )
        val q5Id = questionDao.insert(q5)
        answerDao.insert(Answer(questionId = q5Id.toInt(), text = "Genghis Khan", isCorrect = true))
        answerDao.insert(Answer(questionId = q5Id.toInt(), text = "Attila the Hun", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id.toInt(), text = "Tamerlane", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id.toInt(), text = "Kublai Khan", isCorrect = false))

        // 6. Start of WWII
        val q6 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.fall_of_berlin,
            questionText = "Which event marked the beginning of World War II?"
        )
        val q6Id = questionDao.insert(q6)
        answerDao.insert(Answer(questionId = q6Id.toInt(), text = "Invasion of Poland", isCorrect = true))
        answerDao.insert(Answer(questionId = q6Id.toInt(), text = "Bombing of Pearl Harbor", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id.toInt(), text = "Signing of the Treaty of Versailles", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id.toInt(), text = "Battle of Stalingrad", isCorrect = false))

        // 7. Father of Modern Physics
        val q7 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.albert_einstein,
            questionText = "Who is known as the 'Father of Modern Physics' and developed the theory of relativity?"
        )
        val q7Id = questionDao.insert(q7)
        answerDao.insert(Answer(questionId = q7Id.toInt(), text = "Albert Einstein", isCorrect = true))
        answerDao.insert(Answer(questionId = q7Id.toInt(), text = "Nikola Tesla", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id.toInt(), text = "Isaac Newton", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id.toInt(), text = "Galileo Galilei", isCorrect = false))

        // 8. Greek hero Trojan War
        val q8 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.achilles,
            questionText = "Who was the legendary Greek hero known for his role in the Trojan War?"
        )
        val q8Id = questionDao.insert(q8)
        answerDao.insert(Answer(questionId = q8Id.toInt(), text = "Achilles", isCorrect = true))
        answerDao.insert(Answer(questionId = q8Id.toInt(), text = "Leonidas", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id.toInt(), text = "Alexander the Great", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id.toInt(), text = "Julius Caesar", isCorrect = false))

        // 9. Start of WWI
        val q9 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.world_war_i,
            questionText = "When did World War I start?"
        )
        val q9Id = questionDao.insert(q9)
        answerDao.insert(Answer(questionId = q9Id.toInt(), text = "1914", isCorrect = true))
        answerDao.insert(Answer(questionId = q9Id.toInt(), text = "1915", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id.toInt(), text = "1916", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id.toInt(), text = "1917", isCorrect = false))

        // 10. Start of WWII
        val q10 = Question(
            categoryId = historyCategoryId,
            questionPictureResId = R.drawable.world_war_ii,
            questionText = "When did World War II start?"
        )
        val q10Id = questionDao.insert(q10)
        answerDao.insert(Answer(questionId = q10Id.toInt(), text = "1939", isCorrect = true))
        answerDao.insert(Answer(questionId = q10Id.toInt(), text = "1940", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id.toInt(), text = "1941", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id.toInt(), text = "1942", isCorrect = false))
    }
}



fun seedFlagQuestions(questionDao: QuestionDao, answerDao: AnswerDao, flagCategoryId: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        // 1. France
        val q1 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_france,
            questionText = "Which country's flag is this?"
        )
        val q1Id = questionDao.insert(q1).toInt()
        answerDao.insert(Answer(questionId = q1Id, text = "France", isCorrect = true))
        answerDao.insert(Answer(questionId = q1Id, text = "Italy", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id, text = "Germany", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id, text = "Spain", isCorrect = false))

        // 2. United Kingdom
        val q2 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_america,
            questionText = "Which country's flag is this?"
        )
        val q2Id = questionDao.insert(q2).toInt()
        answerDao.insert(Answer(questionId = q2Id, text = "United States", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id, text = "United Kingdom", isCorrect = true))
        answerDao.insert(Answer(questionId = q2Id, text = "Canada", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id, text = "Australia", isCorrect = false))

        // 3. Mexico
        val q3 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_mexico,
            questionText = "Which country's flag is this?"
        )
        val q3Id = questionDao.insert(q3).toInt()
        answerDao.insert(Answer(questionId = q3Id, text = "Brazil", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id, text = "Argentina", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id, text = "Colombia", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id, text = "Mexico", isCorrect = true))

        // 4. China
        val q4 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_china,
            questionText = "Which country's flag is this?"
        )
        val q4Id = questionDao.insert(q4).toInt()
        answerDao.insert(Answer(questionId = q4Id, text = "Japan", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id, text = "China", isCorrect = true))
        answerDao.insert(Answer(questionId = q4Id, text = "South Korea", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id, text = "India", isCorrect = false))

        // 5. Taiwan
        val q5 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_taiwan,
            questionText = "Which country's flag is this?"
        )
        val q5Id = questionDao.insert(q5).toInt()
        answerDao.insert(Answer(questionId = q5Id, text = "Russia", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id, text = "Ukraine", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id, text = "Taiwan", isCorrect = true))
        answerDao.insert(Answer(questionId = q5Id, text = "Sweden", isCorrect = false))

        // 6. Saudi Arabia
        val q6 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_saudi_arabia,
            questionText = "Which country's flag is this?"
        )
        val q6Id = questionDao.insert(q6).toInt()
        answerDao.insert(Answer(questionId = q6Id, text = "Turkey", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id, text = "Egypt", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id, text = "Saudi Arabia", isCorrect = true))
        answerDao.insert(Answer(questionId = q6Id, text = "Iran", isCorrect = false))

        // 7. South Africa
        val q7 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_sauth_africa,
            questionText = "Which country's flag is this?"
        )
        val q7Id = questionDao.insert(q7).toInt()
        answerDao.insert(Answer(questionId = q7Id, text = "South Africa", isCorrect = true))
        answerDao.insert(Answer(questionId = q7Id, text = "Kenya", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id, text = "Nigeria", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id, text = "Egypt", isCorrect = false))

        // 8. Bosnia
        val q8 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_bosnia_and_herzegovina,
            questionText = "Which country's flag is this?"
        )
        val q8Id = questionDao.insert(q8).toInt()
        answerDao.insert(Answer(questionId = q8Id, text = "Bosnia", isCorrect = true))
        answerDao.insert(Answer(questionId = q8Id, text = "Italy", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id, text = "Portugal", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id, text = "Spain", isCorrect = false))

        // 9. India
        val q9 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_india,
            questionText = "Which country's flag is this?"
        )
        val q9Id = questionDao.insert(q9).toInt()
        answerDao.insert(Answer(questionId = q9Id, text = "Canada", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id, text = "India", isCorrect = true))
        answerDao.insert(Answer(questionId = q9Id, text = "New Zealand", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id, text = "United Kingdom", isCorrect = false))

        // 10. Bulgaria
        val q10 = Question(
            categoryId = flagCategoryId,
            questionPictureResId = R.drawable.flag_buglaria,
            questionText = "Which country's flag is this?"
        )
        val q10Id = questionDao.insert(q10).toInt()
        answerDao.insert(Answer(questionId = q10Id, text = "Bulgaria", isCorrect = true))
        answerDao.insert(Answer(questionId = q10Id, text = "Russia", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id, text = "Bangladesh", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id, text = "Sri Lanka", isCorrect = false))
    }
}
fun seedGeneralQuestions(questionDao: QuestionDao, answerDao: AnswerDao, generalCategoryId: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        // 1. Nile
        val q1 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Which river runs through Egypt?"
        )
        val q1Id = questionDao.insert(q1).toInt()
        answerDao.insert(Answer(questionId = q1Id, text = "Nile", isCorrect = true))
        answerDao.insert(Answer(questionId = q1Id, text = "Amazon", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id, text = "Danube", isCorrect = false))
        answerDao.insert(Answer(questionId = q1Id, text = "Yangtze", isCorrect = false))

        // 2. Mona Lisa
        val q2 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Who painted the Mona Lisa?"
        )
        val q2Id = questionDao.insert(q2).toInt()
        answerDao.insert(Answer(questionId = q2Id, text = "Pablo Picasso", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id, text = "Leonardo da Vinci", isCorrect = true))
        answerDao.insert(Answer(questionId = q2Id, text = "Vincent van Gogh", isCorrect = false))
        answerDao.insert(Answer(questionId = q2Id, text = "Michelangelo", isCorrect = false))

        // 3. Red Planet (incorrect correctAnswerIndex in your data, fixed to Mars)
        val q3 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Which planet is known as the Red Planet?"
        )
        val q3Id = questionDao.insert(q3).toInt()
        answerDao.insert(Answer(questionId = q3Id, text = "Venus", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id, text = "Mars", isCorrect = true))
        answerDao.insert(Answer(questionId = q3Id, text = "Jupiter", isCorrect = false))
        answerDao.insert(Answer(questionId = q3Id, text = "Mercury", isCorrect = false))

        // 4. Largest planet
        val q4 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "What is the largest planet in our solar system?"
        )
        val q4Id = questionDao.insert(q4).toInt()
        answerDao.insert(Answer(questionId = q4Id, text = "Earth", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id, text = "Mars", isCorrect = false))
        answerDao.insert(Answer(questionId = q4Id, text = "Jupiter", isCorrect = true))
        answerDao.insert(Answer(questionId = q4Id, text = "Saturn", isCorrect = false))

        // 5. Theory of relativity
        val q5 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Which scientist developed the theory of relativity?"
        )
        val q5Id = questionDao.insert(q5).toInt()
        answerDao.insert(Answer(questionId = q5Id, text = "Isaac Newton", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id, text = "Albert Einstein", isCorrect = true))
        answerDao.insert(Answer(questionId = q5Id, text = "Galileo Galilei", isCorrect = false))
        answerDao.insert(Answer(questionId = q5Id, text = "Stephen Hawking", isCorrect = false))

        // 6. Chemical symbol for water
        val q6 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "What is the chemical symbol for water?"
        )
        val q6Id =questionDao.insert(q6).toInt()
        answerDao.insert(Answer(questionId = q6Id, text = "H2O2", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id, text = "CO2", isCorrect = false))
        answerDao.insert(Answer(questionId = q6Id, text = "H2O", isCorrect = true))
        answerDao.insert(Answer(questionId = q6Id, text = "O2", isCorrect = false))

        // 7. Romeo and Juliet
        val q7 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Who wrote the play 'Romeo and Juliet'?"
        )
        val q7Id = questionDao.insert(q7).toInt()
        answerDao.insert(Answer(questionId = q7Id, text = "William Shakespeare", isCorrect = true))
        answerDao.insert(Answer(questionId = q7Id, text = "Jane Austen", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id, text = "Charles Dickens", isCorrect = false))
        answerDao.insert(Answer(questionId = q7Id, text = "Leo Tolstoy", isCorrect = false))

        // 8. Tallest mammal
        val q8 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "What is the tallest mammal?"
        )
        val q8Id = questionDao.insert(q8).toInt()
        answerDao.insert(Answer(questionId = q8Id, text = "Elephant", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id, text = "Giraffe", isCorrect = true))
        answerDao.insert(Answer(questionId = q8Id, text = "Whale", isCorrect = false))
        answerDao.insert(Answer(questionId = q8Id, text = "Horse", isCorrect = false))

        // 9. Guacamole
        val q9 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "What is the primary ingredient in guacamole?"
        )
        val q9Id = questionDao.insert(q9).toInt()
        answerDao.insert(Answer(questionId = q9Id, text = "Tomato", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id, text = "Avocado", isCorrect = true))
        answerDao.insert(Answer(questionId = q9Id, text = "Onion", isCorrect = false))
        answerDao.insert(Answer(questionId = q9Id, text = "Lime", isCorrect = false))

        // 10. Red Planet (again, Mars)
        val q10 = Question(
            categoryId = generalCategoryId,
            questionPictureResId = R.drawable.quizlizardlogo,
            questionText = "Which planet is known as the 'Red Planet'?"
        )
        val q10Id = questionDao.insert(q10).toInt()
        answerDao.insert(Answer(questionId = q10Id, text = "Venus", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id, text = "Mars", isCorrect = true))
        answerDao.insert(Answer(questionId = q10Id, text = "Jupiter", isCorrect = false))
        answerDao.insert(Answer(questionId = q10Id, text = "Mercury", isCorrect = false))
    }
}
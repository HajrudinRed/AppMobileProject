package com.example.appquizlizard.backend.di


import android.content.Context
import androidx.room.Room
import com.example.appquizlizard.backend.dao.AnswerDao
import com.example.appquizlizard.backend.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.appquizlizard.backend.dao.CategoryDao
import com.example.appquizlizard.backend.dao.QuestionDao
import com.example.appquizlizard.backend.dao.UserDao
import com.example.appquizlizard.backend.dao.UserProgressDao
import com.example.appquizlizard.backend.repositories.AnswerRepository
import com.example.appquizlizard.backend.repositories.AnswerRepositoryImpl
import com.example.appquizlizard.backend.repositories.CategoryRepository
import com.example.appquizlizard.backend.repositories.CategoryRepositoryImpl
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.QuestionRepositoryImpl
import com.example.appquizlizard.backend.repositories.UserProgressRepository
import com.example.appquizlizard.backend.repositories.UserProgressRepositoryImpl
import com.example.appquizlizard.backend.repositories.UserRepository
import com.example.appquizlizard.backend.repositories.UserRepositoryImpl
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_quiz_lizard_database"
        ).fallbackToDestructiveMigration(true).build()
    }
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()
    @Provides
    fun provideQuestionDao(appDatabase: AppDatabase): QuestionDao = appDatabase.questionDao()
    @Provides
    fun provideUserProgressDao(appDatabase: AppDatabase): UserProgressDao = appDatabase.userProgressDao()
    @Provides
    fun provideAnswerDao(appDatabase: AppDatabase): AnswerDao = appDatabase.answerDao()

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)
    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository = CategoryRepositoryImpl(categoryDao)
    @Provides
    @Singleton
    fun provideQuestionRepository(questionDao: QuestionDao): QuestionRepository = QuestionRepositoryImpl(questionDao)
    @Provides
    @Singleton
    fun provideUserProgressRepository(userProgressDao: UserProgressDao): UserProgressRepository = UserProgressRepositoryImpl(userProgressDao)
    @Provides
    @Singleton
    fun provideAnswerRepository(answerDao: AnswerDao): AnswerRepository = AnswerRepositoryImpl(answerDao)
}


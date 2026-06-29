package com.vtol.zaka.di

import android.content.Context
import androidx.room.Room
import com.vtol.zaka.data.local.AppDatabase
import com.vtol.zaka.data.local.dao.QuizSessionDao
import com.vtol.zaka.data.repository.QuizHistoryRepositoryImpl
import com.vtol.zaka.domain.repository.QuizHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "zaka_db").build()

    @Provides
    fun provideQuizSessionDao(db: AppDatabase): QuizSessionDao = db.quizSessionDao()

    @Provides
    @Singleton
    fun provideQuizHistoryRepository(dao: QuizSessionDao): QuizHistoryRepository =
        QuizHistoryRepositoryImpl(dao)
}
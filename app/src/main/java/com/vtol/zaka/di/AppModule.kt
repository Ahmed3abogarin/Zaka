package com.vtol.zaka.di

import com.google.firebase.ai.GenerativeModel
import com.vtol.zaka.data.repository.QuizRepositoryImpl
import com.vtol.zaka.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizRepository(model: GenerativeModel): QuizRepository =
        QuizRepositoryImpl(model)
}
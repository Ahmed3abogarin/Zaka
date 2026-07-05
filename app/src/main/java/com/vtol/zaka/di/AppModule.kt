package com.vtol.zaka.di

import com.google.firebase.ai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vtol.zaka.data.repository.AppRepositoryImpl
import com.vtol.zaka.data.repository.AuthRepositoryImpl
import com.vtol.zaka.data.repository.QuizRepositoryImpl
import com.vtol.zaka.domain.repository.AppRepository
import com.vtol.zaka.domain.repository.AuthRepository
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


    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository =
        AuthRepositoryImpl(firebaseAuth, firestore)


    @Provides
    @Singleton
    fun provideAppRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AppRepository =
        AppRepositoryImpl(firebaseAuth, firestore)
}
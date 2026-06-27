package com.vtol.zaka.di

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideGenerativeModel() = Firebase.ai.generativeModel(
        modelName = "gemini-3.5-flash",
        generationConfig = generationConfig {
            // 1. Drop temperature down for reliable, factual text generation
            temperature = 0.2f
            // 2. Maximize output headroom so structural JSON blocks don't get truncated
            maxOutputTokens = 8192
            // 3. Enforce valid JSON structure directly at the model layer
            responseMimeType = "application/json"
        }
    )
}
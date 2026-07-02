package com.vtol.zaka.di

import android.content.Context
import com.vtol.zaka.ads.InterstitialAdManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdsModule {

    @Provides
    @Singleton
    fun provideInterstitialAdManager(
        @ApplicationContext context: Context,
    ): InterstitialAdManager = InterstitialAdManager(context)
}
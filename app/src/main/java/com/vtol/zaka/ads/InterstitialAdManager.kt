package com.vtol.zaka.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.vtol.zaka.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class InterstitialAdManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private var interstitialAd: InterstitialAd? = null

    companion object {
        private const val AD_UNIT_ID = BuildConfig.INTERSTITIAL_AD_UNIT
    }

    fun loadAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            context, AD_UNIT_ID, request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) { interstitialAd = ad }
                override fun onAdFailedToLoad(error: LoadAdError) { interstitialAd = null }
            }
        )
    }

    suspend fun showAdAndWait(activity: Activity): Unit = suspendCancellableCoroutine { continuation ->
            val ad = interstitialAd
            if (ad == null) {
                continuation.resume(Unit)   // no ad? proceed silently
                return@suspendCancellableCoroutine
            }

            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadAd()                     // preload next
                    continuation.resume(Unit)    // ← unblocks the coroutine
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    continuation.resume(Unit)    // ← fail gracefully, still unblocks
                }
            }
            ad.show(activity)
        }
}
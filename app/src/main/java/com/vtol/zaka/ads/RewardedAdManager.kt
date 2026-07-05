package com.vtol.zaka.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.vtol.zaka.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RewardedAdManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private var rewardedAd: RewardedAd? = null
    private var isLoading = false

    companion object {
        private const val AD_UNIT_ID = BuildConfig.REWARDED_AD_UNIT
    }

    fun loadAd(onLoaded: () -> Unit, onFailed: () -> Unit) {
        if (isLoading || rewardedAd != null) return
        isLoading = true

        val request = AdRequest.Builder().build()
        RewardedAd.load(
            context, AD_UNIT_ID, request,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    isLoading = false
                    onLoaded()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                    isLoading = false
                    onFailed()
                }
            }
        )
    }

    fun showAd(
        activity: Activity,
        onRewarded: () -> Unit,
        onDismissed: () -> Unit,
    ) {
        val ad = rewardedAd ?: run { onDismissed(); return }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
                loadAd({}, {})   // preload next ad
                onDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                rewardedAd = null
                onDismissed()
            }
        }

        ad.show(activity) { onRewarded() }   // RewardItem callback
    }

    val isAdAvailable get() = rewardedAd != null
}
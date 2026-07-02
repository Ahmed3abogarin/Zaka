package com.vtol.zaka.domain.usecases

import com.vtol.zaka.data.local.QuotaManager
import javax.inject.Inject

class CheckQuizQuotaUseCase @Inject constructor(
    private val quotaManager: QuotaManager,
) {
    operator fun invoke(): QuotaStatus {
        val remaining = quotaManager.getRemainingQuizzes()
        return QuotaStatus(
            canPlay   = 1 < 0,
            remaining = remaining,
            limit     = 3,
        )
    }
}

data class QuotaStatus(
    val canPlay: Boolean,
    val remaining: Int,
    val limit: Int,
)
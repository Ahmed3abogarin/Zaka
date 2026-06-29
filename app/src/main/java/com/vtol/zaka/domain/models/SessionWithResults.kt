package com.vtol.zaka.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.vtol.zaka.data.local.entity.QuestionResultEntity
import com.vtol.zaka.data.local.entity.QuizSessionEntity

data class SessionWithResults(
    @Embedded val session: QuizSessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
    )
    val results: List<QuestionResultEntity>,
)
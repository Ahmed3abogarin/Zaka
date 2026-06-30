package com.vtol.zaka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vtol.zaka.data.local.dao.QuizSessionDao
import com.vtol.zaka.data.local.entity.QuestionResultEntity
import com.vtol.zaka.data.local.entity.QuizSessionEntity

@Database(
    entities = [QuizSessionEntity::class, QuestionResultEntity::class],
    version = 2,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizSessionDao(): QuizSessionDao
}
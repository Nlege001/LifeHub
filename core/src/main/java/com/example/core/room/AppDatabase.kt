package com.example.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.room.user.UserDao
import com.example.core.room.user.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun user(): UserDao
}
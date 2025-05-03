package com.example.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.room.images.ProfileImageEntity
import com.example.core.room.user.DatabaseConverter
import com.example.core.room.user.UserDao
import com.example.core.room.user.UserEntity

@Database(
    entities = [UserEntity::class, ProfileImageEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun user(): UserDao
}
package com.example.core.room.user

import android.util.Base64
import androidx.room.TypeConverter

class DatabaseConverter {
    @TypeConverter
    fun fromByteArray(value: ByteArray?): String? =
        value?.let { Base64.encodeToString(it, Base64.DEFAULT) }

    @TypeConverter
    fun toByteArray(value: String?): ByteArray? =
        value?.let { Base64.decode(it, Base64.DEFAULT) }
}
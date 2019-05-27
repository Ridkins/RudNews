package com.rud.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rud.data.model.Article

@Database(
    entities = [
        Article::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AndroidNewsDb : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
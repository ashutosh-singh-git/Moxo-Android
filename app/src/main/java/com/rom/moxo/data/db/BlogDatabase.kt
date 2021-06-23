package com.rom.moxo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rom.moxo.data.network.response.BlogContentResponse

@Database(
    entities = [BlogContentResponse::class],
    version = 1
)
abstract class BlogDatabase : RoomDatabase() {
    abstract fun blogContentDao(): BlogContentDao

    companion object{
        @Volatile private var instance: BlogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                BlogDatabase::class.java, "moxo.db")
                .build()
    }
}
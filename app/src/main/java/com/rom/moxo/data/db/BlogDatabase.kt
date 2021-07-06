package com.rom.moxo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rom.moxo.data.dao.BlogsDao
import com.rom.moxo.data.dao.RemoteKeysDao
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.data.datamodels.RemoteKeys

@Database(entities = [Content::class, RemoteKeys::class],version = 2,exportSchema = false)
abstract class BlogDatabase : RoomDatabase() {

    abstract val blogsDao: BlogsDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        @Volatile
        private var instance: BlogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BlogDatabase::class.java,
                "moxo_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}
package pl.edu.wat.turboroomdatabasemanagement

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Soldier::class], version = 1, exportSchema = false)
abstract class SoldierDB : RoomDatabase() {
    abstract fun soldierDao() : SoldierDao
    @InternalCoroutinesApi
    companion object {
        private var INSTANCE : SoldierDB? = null
        fun getSoldierDB(context : Context) : SoldierDB? {
            if (INSTANCE == null) {
                kotlinx.coroutines.internal.synchronized(SoldierDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, SoldierDB::class.java, "soldierDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
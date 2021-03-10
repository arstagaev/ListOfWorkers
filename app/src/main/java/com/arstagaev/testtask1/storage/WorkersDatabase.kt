package com.arstagaev.testtask1.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arstagaev.testtask1.models.Specialty
import com.arstagaev.testtask1.models.WorkerInfo
import com.arstagaev.testtask1.models.relations.WorkerInfoCrossSpecialty


@Database(
    entities = [
        WorkerInfo::class,
        Specialty::class,
        WorkerInfoCrossSpecialty::class
    ],
    version = 2
)
abstract class WorkersDatabase : RoomDatabase() {
    abstract val worketInfoDao : WorkerInfoDao

    companion object{
        @Volatile
        private var INSTANCE : WorkersDatabase? = null

        fun getInstance(context: Context): WorkersDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WorkersDatabase::class.java,
                    "workers_db"
                ).build().also { INSTANCE = it}
            }
        }
    }
}
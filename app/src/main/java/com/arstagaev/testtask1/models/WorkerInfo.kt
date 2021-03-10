package com.arstagaev.testtask1.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "worker_info")
data class WorkerInfo(
    @PrimaryKey(autoGenerate = false)
    val idOfWorker: Int,
    val fName: String,
    val lName: String,
    val birthday: String,
    val avatrUrl: String
)

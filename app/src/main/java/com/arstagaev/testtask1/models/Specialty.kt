package com.arstagaev.testtask1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specialty")
data class Specialty(
    @PrimaryKey(autoGenerate = false)
    val specialty_id: Int,
    val name: String

)
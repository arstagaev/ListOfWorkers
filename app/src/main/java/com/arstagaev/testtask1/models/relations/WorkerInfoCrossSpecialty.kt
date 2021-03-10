package com.arstagaev.testtask1.models.relations

import androidx.room.Entity

@Entity(primaryKeys = ["idOfWorker", "specialty_id"])
data class WorkerInfoCrossSpecialty(
    val idOfWorker: Int,
    val specialty_id: Int,
    val name: String
)
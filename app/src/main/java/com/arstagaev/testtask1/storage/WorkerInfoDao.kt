package com.arstagaev.testtask1.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arstagaev.testtask1.models.Specialty
import com.arstagaev.testtask1.models.WorkerInfo
import com.arstagaev.testtask1.models.forjson.SpecialtyJson
import com.arstagaev.testtask1.models.relations.WorkerInfoCrossSpecialty


@Dao
interface WorkerInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkerInfo(workerInfo: WorkerInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSpecialty(specialty: Specialty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSpecialtyCrossWorker(workerInfoCrossSpecialty: WorkerInfoCrossSpecialty)

    @Query("SELECT DISTINCT name, specialty_id from workerinfocrossspecialty")
    fun getSpecialty(): LiveData<List<Specialty>>

    @Query("SELECT idOfWorker, fName, lName, birthday, avatrUrl from worker_info WHERE idOfWorker =:workerId")
    fun getWorkerById(workerId : Int): LiveData<WorkerInfo>

    @Query("SELECT worker_info.idOfWorker, worker_info.fName, worker_info.lName, worker_info.birthday, worker_info.avatrUrl from workerinfocrossspecialty, worker_info WHERE workerinfocrossspecialty.name = :nameOfSpec AND workerinfocrossspecialty.idOfWorker = worker_info.idOfWorker")
    fun getWorkersBySpecialty(nameOfSpec: String): LiveData<List<WorkerInfo>>

    @Query("DELETE FROM worker_info")
    suspend fun deleteWorkers()

    @Query("DELETE FROM workerinfocrossspecialty")
    suspend fun deleteRelationsBetweenWorkersAndSpec()

    @Query("SELECT * FROM worker_info")
    fun getAllWorkers(): LiveData<List<WorkerInfo>>


}
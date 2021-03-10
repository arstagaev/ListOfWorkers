package com.arstagaev.testtask1.repository

import android.app.Application
import com.arstagaev.testtask1.api.RetrofitInstance
import com.arstagaev.testtask1.models.Specialty
import com.arstagaev.testtask1.models.WorkerInfo
import com.arstagaev.testtask1.models.relations.WorkerInfoCrossSpecialty
import com.arstagaev.testtask1.storage.WorkersDatabase

class ListOfWorkersRepository(app : Application)  {
    val db : WorkersDatabase = WorkersDatabase.getInstance(app)
    val dao = db.worketInfoDao

    ///////////////////////////////////////API/////////////////////////////////////
    suspend fun getAlpha() =
        RetrofitInstance.apiAlpha.getAlfaRequest()

//    suspend fun getBeta() =
//        RetrofitInstance.apiBeta.getBetaRequest()

    ///////////////////////////////////Database///////////////////////////////////

    suspend fun saveWorkers(workerInfo: WorkerInfo) =
            db.worketInfoDao.saveWorkerInfo(workerInfo)

    suspend fun saveSpecialty(specialty: Specialty) =
            db.worketInfoDao.saveSpecialty(specialty)

    suspend fun saveSpecialtyCrossWorker(workerInfoCrossSpecialty: WorkerInfoCrossSpecialty) =
        db.worketInfoDao.saveSpecialtyCrossWorker(workerInfoCrossSpecialty)

    fun getAllSpecialty() = db.worketInfoDao.getSpecialty()

    fun getWorkersBySpeciality(nameOfSpec : String) = db.worketInfoDao.getWorkersBySpecialty(nameOfSpec)

    fun getWorkerById(WorkerId : Int) = db.worketInfoDao.getWorkerById(WorkerId)

    // delete
    suspend fun deleteWorkers() =
        db.worketInfoDao.deleteWorkers()

    suspend fun deleteRelations() =
        db.worketInfoDao.deleteRelationsBetweenWorkersAndSpec()

}
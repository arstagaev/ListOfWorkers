package com.arstagaev.testtask1.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arstagaev.testtask1.TestTaskApp
import com.arstagaev.testtask1.models.Specialty
import com.arstagaev.testtask1.models.WorkerInfo
import com.arstagaev.testtask1.models.forjson.SpecialtyJson
import com.arstagaev.testtask1.models.forjson.Workers
import com.arstagaev.testtask1.models.relations.WorkerInfoCrossSpecialty
import com.arstagaev.testtask1.repository.ListOfWorkersRepository
import com.arstagaev.testtask1.utils.Resource
import com.arstagaev.testtask1.utils.validAvatar
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class MainViewModel(app : Application, val  mrepository: ListOfWorkersRepository) :AndroidViewModel(app) {

    private val alphaRequest : MutableLiveData<Resource<Workers>> = MutableLiveData()
    private var allSpec : LiveData<List<Specialty>> = mrepository.getAllSpecialty()
    var checkFragmentVisible : LiveData<Int> = MutableLiveData() // 1-3 screens


    init {

        startAlphaRequest()
        //startBetaRequest()
        Timber.d("init viewModel")


    }


    private fun startAlphaRequest() = viewModelScope.launch {
         safeAplhaRequest()
    }

    private fun startBetaRequest() = viewModelScope.launch {
        //safeBetaRequest()
    }

    private suspend fun safeAplhaRequest() {
        alphaRequest.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()){
                Timber.i("start Alpha request")
                val response = mrepository.getAlpha()
                alphaRequest.postValue(handleAlphaResponse(response))
            }else{
                Timber.i("NO INTERNET!")
            }

        }catch (e : Exception){
            Timber.e("A "+ e)
        }

    }

    private fun handleAlphaResponse(response: Response<Workers>): Resource<Workers>? {
        //catchresponse
        if (response.isSuccessful){

            response.body()?.let {resultResponse ->
                try {
                    Timber.d("answer A: "+resultResponse)
                    //Log.d("pizdec ",resultResponse.name+" //")
                    deleteWorkers()
                    deleteWRelationsWithSpec()
                    val listx = resultResponse.response
                    var id = 0
                    for (i in listx){
                        saveWorker(id,i.fName,i.lName,i.birthday,i.avatrUrl,i.specialty)
                        id++
                    }

                }catch (e : Exception){
                    Timber.e("Error in ViewModel: "+e)
                }
            }
        }else{
            Timber.e("Response is NOT successful"+ response.message())
        }

        return Resource.Error(response.message())
    }

    private fun saveWorker
            (
        idWorker: Int,
        fName: String,
        lName: String,
        birthDay: String,
        avatar: String?,
        specialty: List<SpecialtyJson>
    ) = viewModelScope.launch {
        var changebleBDay = birthDay
        if (changebleBDay == null || changebleBDay == "" || changebleBDay.isEmpty()){
            changebleBDay = "-"
        }

        //Timber.d("ava |" + validAvatar(avatar)+" | ")

        val forecastCell = WorkerInfo(idWorker,fName,lName,changebleBDay,validAvatar(avatar))
        mrepository.saveWorkers(forecastCell)

        for (i in specialty){
            val fff = WorkerInfoCrossSpecialty(idWorker,i.specialtyId,i.name)
            mrepository.saveSpecialtyCrossWorker(fff)
        }

        //////////////////////////////////////////////
    }

    fun getAllSpec() : LiveData<List<Specialty>>{
        return allSpec
    }

    fun getWorkersBySpeciality(nameOfSpec: String) : LiveData<List<WorkerInfo>>{
        var workersBySpecialty : LiveData<List<WorkerInfo>> = mrepository.getWorkersBySpeciality(nameOfSpec)
        return workersBySpecialty
    }

    fun getWorkersByID(id : Int) : LiveData<WorkerInfo>{

        var workersBySpecialty : LiveData<WorkerInfo> = mrepository.getWorkerById(id)

        return workersBySpecialty
    }

    // delete from db
    private fun deleteWorkers() = viewModelScope.launch {
        mrepository.deleteWorkers()
    }

    private fun deleteWRelationsWithSpec() = viewModelScope.launch {
        mrepository.deleteRelations()
    }


    // check internet connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<TestTaskApp>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}
package com.arstagaev.testtask1.api

import com.arstagaev.testtask1.models.forjson.Workers
import retrofit2.Response
import retrofit2.http.GET

interface MassiveAPI {
    // http://api.openweathermap.org/data/2.5/forecast?lat=55.5&lon=37.5&cnt=40&appid=ac79fea59e9d15377b787a610a29b784
    // http://api.openweathermap.org/data/2.5/weather?lat=55.5&lon=37.5&appid=ac79fea59e9d15377b787a610a29b784

    //for 1 day
    @GET("65gb/static/raw/master/testTask.json")
    suspend fun getAlfaRequest() : Response<Workers>

}
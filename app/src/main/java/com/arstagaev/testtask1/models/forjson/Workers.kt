package com.arstagaev.testtask1.models.forjson


import com.google.gson.annotations.SerializedName

data class Workers(
    @SerializedName("response")
    val response: List<Response>
)
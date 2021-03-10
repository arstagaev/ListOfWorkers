package com.arstagaev.testtask1.models.forjson


import com.google.gson.annotations.SerializedName


data class Response(
    @SerializedName("f_name")
    val fName: String,
    @SerializedName("l_name")
    val lName: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("avatr_url")
    val avatrUrl: String,
    @SerializedName("specialty")
    val specialty: List<SpecialtyJson>
)
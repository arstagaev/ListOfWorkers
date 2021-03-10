package com.arstagaev.testtask1.models.forjson


import com.google.gson.annotations.SerializedName


data class SpecialtyJson(
    @SerializedName("specialty_id")
    val specialtyId: Int,
    @SerializedName("name")
    val name: String
)
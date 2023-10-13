package com.dhanush.nyschoolsapp.model

import com.google.gson.annotations.SerializedName

data class School (
    @SerializedName("school_name")
    val schoolName: String?,
    @SerializedName("dbn")
    val dbn: String?
)

data class SatScore(
    val mathScore: String?="",
    val readingScore: String? ="",
    val writingScore: String? = ""
)



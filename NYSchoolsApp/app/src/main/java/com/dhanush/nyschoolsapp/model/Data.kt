package com.dhanush.nyschoolsapp.model

import com.google.gson.annotations.SerializedName

data class School (
    @SerializedName("school_name")
    val schoolName: String?,
    @SerializedName("dbn")
    val dbn: String?
)

data class SchoolDetails(
    @SerializedName("sat_math_avg_score")
    val mathScore: String?,
    @SerializedName("sat_critical_reading_avg_score")
    val readingScore: String?,
    @SerializedName("sat_writing_avg_score")
    val writingScore: String?
)

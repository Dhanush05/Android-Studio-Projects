package com.dhanush.nyschoolsapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolsApi {
    @GET("resource/s3k6-pzi2.json")
    fun getSchools(): Single<List<School>>
    @GET("resource/f9bf-2cp4.json")
    fun getSchoolDetails(@Query("dbn") dbn: String): Single<List<SchoolDetails>>

}
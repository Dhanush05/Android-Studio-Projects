package com.dhanush.nyschoolsapp.di

import com.dhanush.nyschoolsapp.model.SchoolsApi
import com.dhanush.nyschoolsapp.model.SchoolsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class ApiModule {
    private val Base_Uri = "https://data.cityofnewyork.us"
    @Provides
    fun provideSchoolsApi():SchoolsApi{
        return Retrofit.Builder().baseUrl(Base_Uri)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(SchoolsApi::class.java)
    }
    @Provides
    fun provideSchoolsService(): SchoolsService{
        return SchoolsService()
    }
}
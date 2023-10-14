package com.dhanush.nyschoolsapp.di

import com.dhanush.nyschoolsapp.model.SchoolsApi
import com.dhanush.nyschoolsapp.model.SchoolsService
import com.dhanush.nyschoolsapp.repository.SchoolRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class ApiModule {
    private val Base_Uri = "https://data.cityofnewyork.us"
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(intercepter)
    }.build()

    @Provides
    fun provideSchoolsApi():SchoolsApi{
        return Retrofit.Builder().baseUrl(Base_Uri)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build().create(SchoolsApi::class.java)
    }
    @Provides
    fun provideSchoolsService(): SchoolsService{
        return SchoolsService()
    }
    @Provides
    fun provideSchoolRepository(schoolsService: SchoolsService):SchoolRepository{
        return SchoolRepository(schoolsService)
    }
}
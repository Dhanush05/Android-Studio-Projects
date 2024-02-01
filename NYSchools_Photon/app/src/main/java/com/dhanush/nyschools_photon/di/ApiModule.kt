package com.dhanush.nyschools_photon.di

import com.dhanush.nyschools_photon.model.SchoolsApi
import com.dhanush.nyschools_photon.model.SchoolsService
import com.dhanush.nyschools_photon.repository.SchoolRepository
import com.dhanush.nyschools_photon.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://data.cityofnewyork.us"
    //todo: build ok http client f needed later
    val interceptor = HttpLoggingInterceptor().apply{
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().apply{
        this.addInterceptor(interceptor)
    }.build()
    @Provides
    fun provideSchoolsApi(): SchoolsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build().create(SchoolsApi::class.java)
    }
    @Provides
    fun provideSchoolsService(): SchoolsService {
        return SchoolsService()
    }
    @Provides
    fun provideSchoolRepository(schoolsService: SchoolsService): SchoolRepository {
        return SchoolRepository(schoolsService)
    }
    @Provides
    fun provideViewModelFactory(respository: SchoolRepository): ViewModelFactory{
        return ViewModelFactory(respository)
    }
}
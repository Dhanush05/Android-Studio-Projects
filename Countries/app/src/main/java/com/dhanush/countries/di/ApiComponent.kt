package com.dhanush.countries.di

import com.dhanush.countries.model.CountriesService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)
}
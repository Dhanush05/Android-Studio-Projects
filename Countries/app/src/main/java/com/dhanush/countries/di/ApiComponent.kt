package com.dhanush.countries.di

import com.dhanush.countries.model.CountriesService
import com.dhanush.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)
    fun inject(viewModel: ListViewModel)
}
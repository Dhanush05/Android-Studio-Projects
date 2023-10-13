package com.dhanush.nyschoolsapp.di

import com.dhanush.nyschoolsapp.model.SchoolsService
import com.dhanush.nyschoolsapp.viewmodel.DetailsViewModel
import com.dhanush.nyschoolsapp.viewmodel.ListViewModel
import dagger.Component

@Component(modules  = [ApiModule::class])
interface ApiComponent {
    fun inject(service: SchoolsService)
    fun inject(viewModel: ListViewModel)

    fun inject(viewModel: DetailsViewModel)

}
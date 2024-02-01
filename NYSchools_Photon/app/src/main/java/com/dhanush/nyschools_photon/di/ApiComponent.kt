package com.dhanush.nyschools_photon.di

import com.dhanush.nyschools_photon.model.SchoolsService
import com.dhanush.nyschools_photon.view.MainActivity
import com.dhanush.nyschools_photon.view.MainActivity2
import com.dhanush.nyschools_photon.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: SchoolsService)
    fun inject(viewModel: ListViewModel)
    fun inject(activity: MainActivity2)

}
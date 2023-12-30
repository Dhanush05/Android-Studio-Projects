package com.dhanush.countries_graphql.presentation

import androidx.lifecycle.ViewModel
import com.dhanush.countries_graphql.domain.CountryClient
import com.dhanush.countries_graphql.domain.DetailedCountry
import com.dhanush.countries_graphql.domain.GetCountriesUseCase
import com.dhanush.countries_graphql.domain.SimpleCountry
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val countryClient: CountryClient
): ViewModel() {
    data class CountriesState(
        val countries: List<SimpleCountry> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCountry: DetailedCountry? = null
    )
    private val _state  = MutableStateFlow(CountriesState())
    val state: StateFlow<CountriesState> = _state.asStateFlow()
    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    countries = getCountriesUseCase.execute(),
                    isLoading = false
                )
            }

        }
    }
    fun selectCountry(code: String){
        viewModelScope.launch{
            _state.update{
                it.copy(
                    selectedCountry = countryClient.getCountry(code)
                ) }
        }
    }
    fun dismissCountryDialog(){
        _state.update {
            it.copy(
                selectedCountry = null
            )
        }
    }

}
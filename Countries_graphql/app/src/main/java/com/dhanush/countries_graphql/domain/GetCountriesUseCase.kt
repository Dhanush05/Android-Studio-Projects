package com.dhanush.countries_graphql.domain

class GetCountriesUseCase(
    val countryClient: CountryClient
) {
    suspend fun execute(): List<SimpleCountry> {
        return countryClient
            .getCountries()
            .sortedBy { it.name }
    }
}

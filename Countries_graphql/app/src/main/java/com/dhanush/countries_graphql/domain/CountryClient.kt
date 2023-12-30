package com.dhanush.countries_graphql.domain

import com.dhanush.CountryQuery

interface CountryClient {
    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getCountry(code: String): DetailedCountry?
}
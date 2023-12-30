package com.dhanush.countries_graphql.domain

import com.apollographql.apollo3.ApolloClient
import com.dhanush.CountriesQuery
import com.dhanush.CountryQuery

class ApolloCountryClient(
    private val apolloClient: ApolloClient
):CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map {
                it.toSimpleCountry()
//                SimpleCountry(
//                    code = it.code,
//                    name = it.name,
//                    emoji = it.emoji,
//                    capital = it.capital?:"No Capital"
//                )
            } ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
         return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}

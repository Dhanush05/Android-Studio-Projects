package com.dhanush.countries_graphql.domain

import com.dhanush.CountriesQuery
import com.dhanush.CountryQuery

fun CountriesQuery.Country.toSimpleCountry() =
    SimpleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital?:"No Capital"
    )
fun CountryQuery.Country.toDetailedCountry() =
    DetailedCountry(
        code = code,
        name = name,
        capital = capital?:"No Capital",
        emoji = emoji,
        currency = currency?:"No Currency",
        languages = languages.mapNotNull { it.name },
        continent = continent.name
    )
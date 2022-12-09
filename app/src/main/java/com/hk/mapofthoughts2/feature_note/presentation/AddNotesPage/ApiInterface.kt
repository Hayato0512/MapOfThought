package com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers(
        "{X-RapidAPI-Key: 723a040345msh9777641212e2f87p121c09jsn695f86c6a7e8" +
                ", X-RapidAPI-Host: open-weather13.p.rapidapi.com }"
    )
    @GET("latlon/30.438/-89.1028'")
    fun getData():Call<List<WeatherData>>
}
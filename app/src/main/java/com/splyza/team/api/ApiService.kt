package com.splyza.team.api

import com.splyza.team.data.RoleBody
import com.splyza.team.data.Team
import com.splyza.team.data.UrlResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/team/{teamId}")
    fun getTeam(@Path("teamId") teamId: String): Call<Team>

    @POST("/team/{teamId}/invites")
    fun invite(@Path("teamId") teamId: String, @Body role: RoleBody): Call<UrlResponse>

    companion object {
        private var BASE_URL = "http://example.com"
        var INVITE_PATH = "$BASE_URL/ti"
        val RETROFIT: Retrofit by lazy {
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                .build()
        }
    }
}
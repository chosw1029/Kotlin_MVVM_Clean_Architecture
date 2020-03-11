package com.nextus.data.network.api

import com.nextus.data.model.GistEntity
import retrofit2.Call
import retrofit2.http.GET

interface GistsApi {

    @GET("gists/public")
    fun getGists() : Call<List<GistEntity>>

}
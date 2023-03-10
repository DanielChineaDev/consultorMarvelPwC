package com.bpo.consultormarvelpwc.ApiConfig

import com.bpo.consultormarvelpwc.Personaje
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonajeAPI {
    @GET("characters")
    fun getCharacter(@Query("apikey") apikey: String,
                     @Query("ts") ts: String,
                     @Query("hash") hash: String): Call<Personaje>
}
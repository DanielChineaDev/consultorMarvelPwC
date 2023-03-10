package com.bpo.consultormarvelpwc.ApiConfig

import com.bpo.consultormarvelpwc.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelService {
    @GET("characters")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>
}

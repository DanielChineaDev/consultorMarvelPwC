package com.bpo.consultormarvelpwc.ApiConfig

import com.bpo.consultormarvelpwc.Personaje
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

object MarvelAPIService {
        private val retrofit = Retrofit.Builder()
            .baseUrl(MarvelAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service = retrofit.create(MarvelService::class.java)

        fun getCharacters(): List<Personaje>? {
            val timestamp = System.currentTimeMillis()
            val hash = md5(timestamp.toString() + MarvelAPI.PRIVATE_API_KEY + MarvelAPI.PUBLIC_API_KEY)
            val response = service.getCharacters(MarvelAPI.PUBLIC_API_KEY, timestamp, hash).execute()

            if (response.isSuccessful) {
                val dataWrapper = response.body()
                return dataWrapper?.data?.results
            }

            return null
        }

        private fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(input.toByteArray())
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }
    }

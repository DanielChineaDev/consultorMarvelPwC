package com.bpo.consultormarvelpwc

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val data: CharacterDataContainer
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Personaje>
)
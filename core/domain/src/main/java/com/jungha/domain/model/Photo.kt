package com.jungha.domain.model

data class Photo(
    val id: String,
    val name: String,
    val albumName: String,
    val image: String? = null
)

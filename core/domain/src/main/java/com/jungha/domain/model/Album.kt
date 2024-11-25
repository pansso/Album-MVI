package com.jungha.domain.model

data class Album(
    val id: String,
    val name : String,
    val photoCount : Int,
    val thumbnail: String? = null
)

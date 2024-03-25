package com.vanya.movieapp.model

import com.google.gson.annotations.SerializedName

data class Genre(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("id")
    val id: Int = -1
)
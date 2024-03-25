package com.vanya.movieapp.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(

    @field:SerializedName("genres")
    val genres: List<Genre>? = null
)
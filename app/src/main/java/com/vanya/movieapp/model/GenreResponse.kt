package com.vanya.movieapp.model

import com.squareup.moshi.Json

data class GenreResponse(

	@Json(name="genres")
	val genres: List<Genre>? = null
)
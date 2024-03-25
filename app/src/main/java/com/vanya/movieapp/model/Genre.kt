package com.vanya.movieapp.model

import com.squareup.moshi.Json

data class Genre(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null
)
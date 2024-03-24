package com.vanya.movieapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    @Json(name = "overview")
    val overview: String? = null,

    @Json(name = "original_language")
    val originalLanguage: String? = null,

    @Json(name = "original_title")
    val originalTitle: String? = null,

    @Json(name = "video")
    val video: Boolean? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "genre_ids")
    val genreIds: List<Int>? = null,

    @Json(name = "poster_path")
    val posterPath: String? = null,

    @Json(name = "backdrop_path")
    val backdropPath: String? = null,

    @Json(name = "release_date")
    val releaseDate: String? = null,

    @Json(name = "popularity")
    val popularity: Float? = null,

    @Json(name = "vote_average")
    val voteAverage: Float? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "adult")
    val adult: Boolean? = null,

    @Json(name = "vote_count")
    val voteCount: Int? = null
):Parcelable {

    //release date ex
    //2024-02-14
    //2024
    fun yearRelease() = releaseDate?.take(4)

    fun percentageScore(): String {
        val average = voteAverage?.times(10)
        return "${average?.toInt()}%"
    }

    fun fullPathUrl() : String = "https://image.tmdb.org/t/p/w342$posterPath"
}
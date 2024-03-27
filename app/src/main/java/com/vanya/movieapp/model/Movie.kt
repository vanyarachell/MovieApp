package com.vanya.movieapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vanya.movieapp.util.DataGenres
import kotlinx.parcelize.Parcelize

@Entity
    (
    tableName = "movies"
)
@Parcelize
data class Movie(
    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Float? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Float? = null,

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    var personalRating: Int = 0,

    var isFavorite: Boolean = false

) : Parcelable {

    //release date ex
    //2024-02-14
    //2024
    fun yearRelease() = releaseDate?.take(4)

    fun percentageScore(): String {
        val average = voteAverage?.times(10)
        return "${average?.toInt()}%"
    }

    fun percentageScoreWithoutSymbol(): Int {
        return (voteAverage?.times(10) ?: 0.0).toInt()
    }

    fun fullPathUrl(): String = "https://image.tmdb.org/t/p/w342$posterPath"
    fun fullBackdropUrl(): String = "https://image.tmdb.org/t/p/w342$backdropPath"

    fun getListGenre(): List<String> {
        val listString = arrayListOf<String>()
        DataGenres.getGenreList().forEach { genre ->
            genreIds?.forEach { ids ->
                genre.id.let {
                    if (ids == it) {
                        genre.name.let { name ->
                            listString.add(name)
                        }
                    }
                }
            }
        }
        return listString
    }

    fun stringPersonalRating(): String {
        return personalRating.toString()
    }
}
package com.vanya.movieapp.util

import com.google.gson.Gson
import com.vanya.movieapp.model.Genre
import com.vanya.movieapp.model.GenreResponse


/**
 * Created by vanyarachell on Tue, 26 Mar 2024
 * vanyarachel05@gmail.com
 */
object DataGenres {

    private var genreJSON: String? = "{\n" +
            "  \"genres\": [\n" +
            "    {\n" +
            "      \"id\": 28,\n" +
            "      \"name\": \"Action\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 12,\n" +
            "      \"name\": \"Adventure\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 16,\n" +
            "      \"name\": \"Animation\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 35,\n" +
            "      \"name\": \"Comedy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 80,\n" +
            "      \"name\": \"Crime\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 99,\n" +
            "      \"name\": \"Documentary\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 18,\n" +
            "      \"name\": \"Drama\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10751,\n" +
            "      \"name\": \"Family\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 14,\n" +
            "      \"name\": \"Fantasy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 36,\n" +
            "      \"name\": \"History\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 27,\n" +
            "      \"name\": \"Horror\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10402,\n" +
            "      \"name\": \"Music\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9648,\n" +
            "      \"name\": \"Mystery\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10749,\n" +
            "      \"name\": \"Romance\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 878,\n" +
            "      \"name\": \"Science Fiction\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10770,\n" +
            "      \"name\": \"TV Movie\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 53,\n" +
            "      \"name\": \"Thriller\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10752,\n" +
            "      \"name\": \"War\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 37,\n" +
            "      \"name\": \"Western\"\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    fun getGenreList(): List<Genre> {
        val gson = Gson()
        val genreResponse = gson.fromJson(genreJSON, GenreResponse::class.java)
        return genreResponse.genres
    }
}
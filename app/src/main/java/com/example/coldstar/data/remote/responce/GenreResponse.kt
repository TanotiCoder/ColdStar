package com.example.coldstar.data.remote.responce

import com.example.coldstar.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)
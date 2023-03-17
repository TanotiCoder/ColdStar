package com.example.coldstar.data.remote.responce

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castResult: List<Cast>
)

@Parcelize
/**Unused attributes were not included here*/
data class Cast(
    @SerializedName("known_for_department")
    val department: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable
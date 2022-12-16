package com.example.vkalbums.models.albumModel

import com.google.gson.annotations.SerializedName

/**
 * Number of albums and list of albums
 */
data class AlbumsInfo(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Album>
)

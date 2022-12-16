package com.example.vkalbums.models.photosModel

import com.google.gson.annotations.SerializedName

/**
 * Woooow, that is photo class!!
 */
data class Photo(
    @SerializedName("sizes") var sizes: List<PhotoSize>
)
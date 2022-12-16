package com.example.vkalbums.models.photosModel

import com.google.gson.annotations.SerializedName

/**
 * URL of image
 */
data class PhotoSize(
    @SerializedName("url") var url: String
)

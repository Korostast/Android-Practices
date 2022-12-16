package com.example.vkalbums.models.photosModel

import com.google.gson.annotations.SerializedName

/**
 * Number of photos in album and photos itself
 */
data class PhotosInfo(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Photo>
)

package com.example.vkalbums.models.photosModel

import com.example.vkalbums.models.commonModel.VkError
import com.google.gson.annotations.SerializedName

/**
 * VK API response for getAlbums request
 */
data class GetPhotosResponse(
    @SerializedName("response") var response: PhotosInfo,
    @SerializedName("error") var error: VkError
)

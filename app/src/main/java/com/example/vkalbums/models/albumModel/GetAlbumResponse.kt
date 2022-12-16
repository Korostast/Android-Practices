package com.example.vkalbums.models.albumModel

import com.example.vkalbums.models.commonModel.VkError
import com.google.gson.annotations.SerializedName

/**
 * VK API response for getAlbums request
 */
data class GetAlbumResponse(
    @SerializedName("response") var response: AlbumsInfo,
    @SerializedName("error") var error: VkError
)
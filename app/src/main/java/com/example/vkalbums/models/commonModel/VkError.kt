package com.example.vkalbums.models.commonModel

import com.google.gson.annotations.SerializedName

/**
 * VK API error code
 */
data class VkError(
    @SerializedName("error_code") val code: Int
)

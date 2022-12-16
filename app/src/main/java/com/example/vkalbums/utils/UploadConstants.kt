package com.example.vkalbums.utils

/**
 * Constants for loading images and albums
 */
object UploadConstants {
    // The number of last albums in the Recycler View, after loading which the background loading of the next package of albums will start
    const val ALBUM_OFFSET = 5

    // Number of albums in one package
    const val ALBUM_COUNT = 20

    // The number of last photos in the Recycler View, after loading which the background loading of the next package of albums will start
    const val PHOTO_OFFSET = 10

    // Number of photos in one package
    const val PHOTO_COUNT = 50
}
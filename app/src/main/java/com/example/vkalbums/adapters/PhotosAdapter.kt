package com.example.vkalbums.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkalbums.PhotosActivity
import com.example.vkalbums.R
import com.example.vkalbums.utils.UploadConstants.PHOTO_COUNT
import com.example.vkalbums.utils.UploadConstants.PHOTO_OFFSET

/**
 * Photos recycler view adapter. Upload images via Glide and display it in grid view
 */
class PhotosAdapter(
    private val userId: String,
    private val albumId: Int,
    private val context: Context
) :
    RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {

    private var photosUrl = ArrayList<String>()
    private var curPhotosSize = 0   // Number of uploaded photos
    var maxSize = 0                 // Number of photos in album
    var uploading = false    // If we uploading new images package, then we stop send requests to VK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotosHolder(view)
    }


    /**
     * Upload photos asynchronously
     */
    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        // If the Internet connection is lost, we can load pictures from cache
        Glide.with(context).load(photosUrl[position])
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.photoImage)

        // Shall we upload new package of images?
        if (curPhotosSize < maxSize && !uploading && position >= curPhotosSize - PHOTO_OFFSET) {
            uploading = true
            (context as PhotosActivity).uploadPictures(
                this,
                userId,
                photosUrl.size,
                PHOTO_COUNT,
                albumId
            )
        }
    }

    override fun getItemCount(): Int {
        return photosUrl.size
    }

    /**
     * Add new elements to recycler view
     */
    fun addData(photosUrl: List<String>) {
        val prevPhotosSize = this.photosUrl.size
        this.photosUrl += photosUrl
        curPhotosSize = this.photosUrl.size
        notifyItemRangeInserted(prevPhotosSize, photosUrl.size)
    }

    inner class PhotosHolder(item: View) : RecyclerView.ViewHolder(item) {
        var photoImage: ImageView = item.findViewById(R.id.photo_image)
    }
}
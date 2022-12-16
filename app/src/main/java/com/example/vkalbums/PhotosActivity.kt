package com.example.vkalbums

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.vkalbums.adapters.PhotosAdapter
import com.example.vkalbums.api.RetrofitClient
import com.example.vkalbums.api.VkInterface
import com.example.vkalbums.models.photosModel.GetPhotosResponse
import com.example.vkalbums.models.photosModel.Photo
import com.example.vkalbums.utils.UploadConstants
import com.example.vkalbums.utils.vkErrorHandler
import com.google.android.flexbox.*
import retrofit2.Response

/**
 * Displays photos of specific album
 */
class PhotosActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        val photosView: RecyclerView = findViewById(R.id.photos_view)
        errorLabel = findViewById(R.id.photos_placeholder)

        val albumId: Int = intent.getIntExtra("album_id", -1)
        val userId: String = intent.getStringExtra("user_id")!!
        val albumName: String = intent.getStringExtra("album_name")!!
        title = albumName

        // Great layout
        photosView.layoutManager = FlexboxLayoutManager(this@PhotosActivity).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = PhotosAdapter(userId, albumId, this@PhotosActivity)
        photosView.adapter = adapter

        // Upload first photos package
        uploadPictures(adapter, userId, 0, UploadConstants.PHOTO_COUNT, albumId)
    }

    /**
     * Upload new photos package of 'count' albums beginning from 'offset' from album
     * with 'album_id'asynchronously
     */
    fun uploadPictures(
        adapter: PhotosAdapter,
        userId: String,
        offset: Int,
        count: Int,
        albumId: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VkInterface::class.java)
            var response: Response<GetPhotosResponse>? = null

            try {
                response = api.getPhotos(
                    userId,
                    albumId,
                    offset,
                    count,
                    1
                )
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_photos)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val photos: List<Photo> = response.body()!!.response.items
                        val photosUrl = ArrayList<String>()
                        for (photo in photos) {
                            photosUrl.add(photo.sizes[6].url)
                        }

                        adapter.addData(photosUrl)
                        adapter.uploading = false
                        adapter.maxSize = response.body()!!.response.count
                    }
                } else {
                    errorLabel.text = getString(R.string.internet_con)
                    errorLabel.visibility = View.VISIBLE
                }
            } catch (Ex: Exception) {
                val code = response?.body()?.error?.code
                vkErrorHandler(this@PhotosActivity, errorLabel, code, Ex)
            }
        }
    }
}
package com.example.vkalbums

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.vkalbums.adapters.AlbumsAdapter
import com.example.vkalbums.api.RetrofitClient
import com.example.vkalbums.api.VkInterface
import com.example.vkalbums.models.albumModel.Album
import com.example.vkalbums.models.albumModel.GetAlbumResponse
import com.example.vkalbums.utils.UploadConstants
import com.example.vkalbums.utils.vkErrorHandler
import com.google.android.flexbox.*
import retrofit2.Response

/**
 * Displays user's albums
 */
class AlbumsActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        val albumsView: RecyclerView = findViewById(R.id.albums_view)
        errorLabel = findViewById(R.id.albums_placeholder)

        val userId = intent.getStringExtra("user_id")!!

        // Great layout
        albumsView.layoutManager = FlexboxLayoutManager(this@AlbumsActivity).apply {
            justifyContent = JustifyContent.SPACE_AROUND
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = AlbumsAdapter(userId, this@AlbumsActivity)
        albumsView.adapter = adapter

        // Upload first albums package
        uploadAlbums(adapter, userId, 0, UploadConstants.ALBUM_COUNT)
    }

    /**
     * Upload new albums package of 'count' albums beginning from 'offset' asynchronously
     */
    fun uploadAlbums(
        adapter: AlbumsAdapter,
        userId: String,
        offset: Int,
        count: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VkInterface::class.java)
            var response: Response<GetAlbumResponse>? = null

            try {
                response = api.getAlbums(userId, offset, count, 1)
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_albums)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val albums: List<Album> = response.body()!!.response.items
                        adapter.addData(albums)
                        adapter.uploading = false
                        adapter.maxSize = response.body()!!.response.count
                    }
                } else {
                    errorLabel.text = getString(R.string.internet_con)
                    errorLabel.visibility = View.VISIBLE
                }
            } catch (Ex: Exception) {
                val code = response?.body()?.error?.code
                vkErrorHandler(this@AlbumsActivity, errorLabel, code, Ex)
            }
        }
    }
}
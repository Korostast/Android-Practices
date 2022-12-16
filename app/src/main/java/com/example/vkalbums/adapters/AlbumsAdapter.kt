package com.example.vkalbums.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkalbums.AlbumsActivity
import com.example.vkalbums.PhotosActivity
import com.example.vkalbums.R
import com.example.vkalbums.models.albumModel.Album
import com.example.vkalbums.utils.UploadConstants.ALBUM_COUNT
import com.example.vkalbums.utils.UploadConstants.ALBUM_OFFSET

/**
 * Albums recycler view adapter. Upload images via Glide and display it in grid view
 */
class AlbumsAdapter(private val userId: String, private val context: Context) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder>() {

    private var albums = ArrayList<Album>()
    private var curAlbumsSize = 0   // Number of uploaded albums
    var maxSize = 0;                // Number of user albums
    var uploading = false   // If we uploading new albums package, then we stop send requests to VK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return AlbumsHolder(view)
    }

    /**
     * Upload albums thumbs asynchronously
     */
    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        holder.albumName.text = albums[position].title
        holder.albumImage.setOnClickListener(openAlbum(position))
        holder.albumCard.setOnClickListener(openAlbum(position))

        // If the Internet connection is lost, we can load pictures from cache
        Glide.with(context).load(albums[position].thumbSrc)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.albumImage)

        // Shall we upload new package of images?
        if (curAlbumsSize < maxSize && !uploading && position >= curAlbumsSize - ALBUM_OFFSET) {
            uploading = true
            (context as AlbumsActivity).uploadAlbums(this, userId, albums.size, ALBUM_COUNT)
        }
    }

    /**
     * Open album by clicking on it
     */
    private fun openAlbum(position: Int): (v: View) -> Unit = {
        val intent = Intent(context, PhotosActivity::class.java)
        intent.putExtra("album_id", albums[position].id)
        intent.putExtra("album_name", albums[position].title)
        intent.putExtra("user_id", userId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    /**
     * Add new elements to recycler view
     */
    fun addData(albums: List<Album>) {
        val prevAlbumsSize = this.albums.size
        this.albums += albums
        curAlbumsSize = this.albums.size
        notifyItemRangeInserted(prevAlbumsSize, albums.size)
    }

    inner class AlbumsHolder(item: View) : RecyclerView.ViewHolder(item) {
        var albumName: TextView = item.findViewById(R.id.album_name_text)
        var albumImage: ImageButton = item.findViewById(R.id.album_image_button)
        var albumCard: CardView = item.findViewById(R.id.album_card)
    }
}
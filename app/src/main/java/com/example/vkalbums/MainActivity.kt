package com.example.vkalbums

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity with user input
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userIdEdit: EditText = findViewById(R.id.user_id_edit)
        val getAlbumsButton: Button = findViewById(R.id.get_albums_button)
        getAlbumsButton.setOnClickListener {
            val userId = userIdEdit.text.toString()
            if (userId.isNotEmpty()) {
                val intent = Intent(this@MainActivity, AlbumsActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
            }
        }
    }
}
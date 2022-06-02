package com.example.exoplayerapp

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exoplayerapp.adapters.VideosAdapter
import com.example.exoplayerapp.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val videoList = mutableListOf<MediaItem>()
    private val videoAdapter:VideosAdapter = VideosAdapter(videoList)
    private lateinit var player: ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        player = ExoPlayer.Builder(applicationContext).build()
        binding.playerView.player = player
        askPermissions()
        loadVideos()
        //player.setMediaItem(videoList[0])
        //player.prepare()
        //player.play()
        initRecyclerView()

    }
    fun initRecyclerView(){
       binding.videoList.adapter = videoAdapter
       binding.videoList.layoutManager = LinearLayoutManager(this)
    }
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            loadVideos()
        }
    fun askPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(!Environment.isExternalStorageManager()){
                val i = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                getResult.launch(i)
            }
        }


    }
    fun loadVideos(){
        val resolver: ContentResolver = contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor  = resolver.query(uri, null, null, null, null)
        when {
            cursor == null -> {
                // query failed, handle error.
                Toast.makeText(this, "Ups hubo un error", Toast.LENGTH_SHORT).show()
            }
            !cursor.moveToFirst() -> {
                // no media on the device
                Toast.makeText(this, "No se encontraron Canciones", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val idColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val idTitle: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                do {
                    val thisId = cursor.getLong(idColumn)
                    val thisTitle = cursor.getLong(idTitle)
                    val contentUri: Uri =
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId )
                    val videoItem = MediaItem.fromUri(contentUri)
                    videoList.add(videoItem)
                    videoAdapter.notifyItemInserted(videoList.size-1)

                    player.addMediaItem(videoItem)
                    //player.play()

                } while (cursor.moveToNext())
                player.prepare()
                Toast.makeText(this, "${videoList.size} videos encontrados", Toast.LENGTH_SHORT).show()
            }
        }
        cursor?.close()
    }
}
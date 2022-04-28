package com.example.exoplayerapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerapp.databinding.VideoItemBinding
import com.google.android.exoplayer2.MediaItem

class VideosViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val binding = VideoItemBinding.bind(view)

    fun render(video:MediaItem){
        //binding.videoTitle.text = "video"
        binding.videoTitle.text = video.mediaMetadata.title.toString()
        //binding.videoThumbnail.setImageURI(video.mediaMetadata.)
    }
}
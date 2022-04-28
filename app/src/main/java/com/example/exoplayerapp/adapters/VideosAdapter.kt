package com.example.exoplayerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerapp.R
import com.google.android.exoplayer2.MediaItem

class VideosAdapter(private val videos:List<MediaItem>):RecyclerView.Adapter<VideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return VideosViewHolder(layoutInflater.inflate(R.layout.video_item,parent,false))
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.render(videos[position])
    }

    override fun getItemCount(): Int {
        return videos.size
    }

}
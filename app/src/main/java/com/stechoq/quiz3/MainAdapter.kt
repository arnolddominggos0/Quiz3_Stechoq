package com.stechoq.quiz3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stechoq.quiz3.databinding.ListItemBinding
import com.stechoq.quiz3.model.PhotoModel

class MainAdapter(private val itemList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ListItemBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                val binding = ListItemBinding.inflate(inflater, parent, false)
                PhotoViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]

        when (holder) {
            is HeaderViewHolder -> {
                val headerItem = item as String
                holder.bindHeader(headerItem)
            }
            is PhotoViewHolder -> {
                val photoItem = item as PhotoModel
                holder.bindPhoto(photoItem)
            }
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] is String) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    // ViewHolder untuk tampilan header album
    private class HeaderViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindHeader(albumTitle: String) {
            binding.albumTitleTextView.text = albumTitle
            binding.albumTitleTextView.visibility = ViewGroup.VISIBLE
        }
    }

    // ViewHolder untuk tampilan item foto
    private class PhotoViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPhoto(photo: PhotoModel) {
            // Menggunakan Glide untuk memuat gambar dari URL
            Glide.with(binding.root.context)
                .load(photo.thumbnailUrl)
                .placeholder(R.drawable.baseline_image_24)
                .into(binding.thumbnailImageView)

            binding.titleTextView.text = photo.title
            binding.albumTitleTextView.text = photo.albumId.toString()
            binding.albumTitleTextView.visibility = ViewGroup.VISIBLE
        }
    }
}

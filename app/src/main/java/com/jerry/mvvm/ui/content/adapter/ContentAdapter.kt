package com.jerry.mvvm.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cookpad.hiring.android.ui.listener.OnItemListener
import com.jerry.mvvm.R
import com.jerry.mvvm.model.remote.Content
import com.jerry.mvvm.databinding.ViewholderContentBinding



class ContentAdapter()
    : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    var contents = mutableListOf<Content>()
    var contentsFiltered = mutableListOf<Content>()


    interface OnItemClickListener{
        fun onItemClicked(item : Content)
    }

    private lateinit var listener: OnItemListener

    open fun setListener(listener: OnItemListener) {
        this.listener = listener
    }

    fun setContentList(contents: List<Content>?) {
        if (contents!=null) {
            this.contents = contents.toMutableList()
            this.contentsFiltered = contents.toMutableList()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(
            contents[position], listener
        )

        holder.itemView.setOnClickListener {
            listener.onItemClicked(contents[position])
        }
    }

    override fun getItemCount(): Int {
        return contents.size
    }


    class ContentViewHolder(private val binding: ViewholderContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var content: Content? = null

        fun bind(content: Content?, listener: OnItemListener) {
            this.content = content

            content?.let {
                binding.tvTitle.text = content.title
                binding.tvSubTitle.text =content.subtitle
                binding.tvDate.text = content.date


                Glide.with(binding.root.context)
                    .load(content.previewImageUrls?.firstOrNull())
                    .centerCrop()
                    .into(binding.imageView)

                binding.favouriteImageView.setImageResource(
                    if (content.liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )

                binding.favouriteImageView.setOnClickListener {
                    listener.onHeartClicked(content)
                    content.liked = !content.liked
                    bind(content, listener)
                }
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ContentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewholderContentBinding.inflate(layoutInflater, parent, false)
            return ContentViewHolder(binding)
        }
    }

}
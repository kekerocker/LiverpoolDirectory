package com.dsoft.liverpooldirectory.ui.social.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.model.VKWall
import com.dsoft.liverpooldirectory.utility.getTime

class SocialRecyclerAdapter : RecyclerView.Adapter<SocialRecyclerAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<VKWall>() {

        override fun areItemsTheSame(oldItem: VKWall, newItem: VKWall): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: VKWall, newItem: VKWall): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class MyViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews
        val itemImages: ImageView = binding.ivSocial
        val itemDate: TextView = binding.tvDate

        init {
            itemView.setOnClickListener {
                val postId = differ.currentList[layoutPosition].postId
                val bundle = Bundle()

                bundle.putInt("postId", postId)
                itemView.findNavController().navigate(R.id.action_socialFragment_to_commentSectionFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSocialNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        //VK creating date is in seconds, so I transform it to millis
        val currentTimeInMillis = currentItem.date * 1000

        holder.itemTitle.text = currentItem.text
        holder.itemLikes.text = currentItem.likesCount.toString()
        holder.itemComments.text = currentItem.commentsCount.toString()
        holder.itemViews.text = currentItem.viewCount.toString()
        holder.itemDate.text = getTime(currentTimeInMillis)

        val layoutParams = holder.itemImages.layoutParams
        layoutParams.width = currentItem.imageWidth
        layoutParams.height = currentItem.imageHeight
        holder.itemImages.load(currentItem.image) {
            crossfade(true)
            crossfade(250)
            transformations(RoundedCornersTransformation(20f))
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
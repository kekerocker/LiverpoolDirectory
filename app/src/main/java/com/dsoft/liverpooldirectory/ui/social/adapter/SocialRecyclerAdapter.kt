package com.dsoft.liverpooldirectory.ui.social.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.model.vk.wall.Item
import com.dsoft.liverpooldirectory.repository.AppPreferences
import com.dsoft.liverpooldirectory.ui.social.DialogSendCommentFragment
import dagger.hilt.android.internal.managers.ViewComponentManager

class SocialRecyclerAdapter constructor(val context: Context) : RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    val appPreferences by lazy { AppPreferences(context) }

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    inner class ViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews
        val itemImages: ImageView = binding.ivSocial

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                appPreferences.savePostId(differ.currentList[position].id.toString())

                val manager = (activityContext() as AppCompatActivity).supportFragmentManager
                val dialog = DialogSendCommentFragment()
                dialog.show(manager, "test")
            }
        }

        private fun activityContext(): Context? {
            val context = itemView.context
            return if (context is ViewComponentManager.FragmentContextWrapper) {
                context.baseContext
            } else context
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemSocialNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        val attachments = currentItem.attachments!!

        Log.d("TestImage", "TEST: $position")

        holder.itemTitle.text = currentItem.text
        holder.itemLikes.text = currentItem.likes.count.toString()
        holder.itemComments.text = currentItem.comments.count.toString()
        holder.itemViews.text = currentItem.views.count.toString()

        val attachment = attachments.firstOrNull() ?: return
        val photo = attachment.photo.sizes.firstOrNull { size -> size.type == "r" } ?: return
        val layoutParams = holder.itemImages.layoutParams
        layoutParams.width = photo.width
        layoutParams.height = photo.height
        holder.itemImages.load(photo.url)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.fragments.social.DialogSendCommentFragment
import com.dsoft.liverpooldirectory.model.vk.wall.Item
import com.dsoft.liverpooldirectory.repository.AppPreferences
import dagger.hilt.android.AndroidEntryPoint

class SocialRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    var list: List<Item> = emptyList()
        set(value) {
            if (value.isNotEmpty()) field = value
            notifyDataSetChanged()
        }

    private var appPreferences: AppPreferences? = null

    inner class ViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews
        val itemImages: ImageView = binding.ivSocial

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                Log.d("PositionComment", list[position].id.toString())
                appPreferences?.savePosition(list[position].id.toString())

                val manager = (context as AppCompatActivity).supportFragmentManager
                val dialog = DialogSendCommentFragment()
                dialog.show(manager, "test")
            }
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
        val currentItem = list[position]
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
        return list.size
    }
}
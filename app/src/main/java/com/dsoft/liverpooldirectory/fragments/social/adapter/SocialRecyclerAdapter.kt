package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.fragments.social.DialogSendCommentFragment
import com.dsoft.liverpooldirectory.model.vk.wall.Item

class SocialRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    var list: List<Item> = emptyList()
        set(value) {
            if (value.isNotEmpty()) field = value
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
        appPreferences = AppPreferences(context)
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
        val attachments = currentItem.attachments

        Log.d("TestImage", "TEST: $position")

        holder.itemTitle.text = currentItem.text
        holder.itemLikes.text = currentItem.likes.count.toString()
        holder.itemComments.text = currentItem.comments.count.toString()
        holder.itemViews.text = currentItem.views.count.toString()

        if (currentItem.marked_as_ads == 1 || attachments.size > 1 || attachments.flatMap { it.photo.sizes }.isEmpty()) {
            holder.itemImages.visibility = View.GONE
        } else {
            holder.itemImages.load(attachments.flatMap { it -> it.photo.sizes.map { it.url } }.last())
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
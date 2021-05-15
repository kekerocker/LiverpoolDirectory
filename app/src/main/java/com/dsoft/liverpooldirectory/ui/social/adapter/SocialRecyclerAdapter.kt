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
import com.dsoft.liverpooldirectory.model.VKWall
import com.dsoft.liverpooldirectory.other.getTime
import com.dsoft.liverpooldirectory.repository.AppPreferences
import com.dsoft.liverpooldirectory.ui.social.DialogSendCommentFragment
import dagger.hilt.android.internal.managers.ViewComponentManager

class SocialRecyclerAdapter constructor(val context: Context) : RecyclerView.Adapter<SocialRecyclerAdapter.MyViewHolder>() {

    val appPreferences by lazy { AppPreferences(context) }

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
                val position: Int = adapterPosition
                appPreferences.savePostId(differ.currentList[position].postId.toString())

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

        Log.d("TestImage", "TEST: ${currentItem.date}")


        val layoutParams = holder.itemImages.layoutParams
        layoutParams.width = currentItem.imageWidth
        layoutParams.height = currentItem.imageHeight
        holder.itemImages.load(currentItem.image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
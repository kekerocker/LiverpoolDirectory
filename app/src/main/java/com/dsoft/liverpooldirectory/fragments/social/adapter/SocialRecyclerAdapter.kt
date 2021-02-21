package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding
import com.dsoft.liverpooldirectory.fragments.social.DialogSendCommentFragment


class SocialRecyclerAdapter(
    private var text: List<String>,
    private var likes: List<String>,
    private var comments: List<String>,
    private var views: List<String>,
    private var id: List<String>,
    //private var date: List<Int>,
    private val context: Context,
) : RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    private var appPreferences: AppPreferences? = null

    inner class ViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                appPreferences?.savePosition(id[position])

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
        return ViewHolder(ItemSocialNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = text[position]
        holder.itemLikes.text = likes[position]
        holder.itemComments.text = comments[position]
        holder.itemViews.text = views[position]
    }

    override fun getItemCount(): Int {
        return text.size
    }
}
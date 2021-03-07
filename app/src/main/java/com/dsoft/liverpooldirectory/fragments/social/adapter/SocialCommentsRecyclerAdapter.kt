package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.databinding.ItemCommentBinding
import com.dsoft.liverpooldirectory.model.vk.comments.Item

class SocialCommentsRecyclerAdapter: RecyclerView.Adapter<SocialCommentsRecyclerAdapter.ViewHolder>() {

    var commentsList: List<Item> = emptyList()
        set(value) {
            if (value.isNotEmpty()) field = value
        }

    inner class ViewHolder(binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemText: TextView = binding.tvCommentsText
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SocialCommentsRecyclerAdapter.ViewHolder {
        return ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SocialCommentsRecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = commentsList[position]
        holder.itemText.text = currentItem.text

    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    fun setData(comments: List<Item>) {
        this.commentsList = comments
        notifyDataSetChanged()
    }
}
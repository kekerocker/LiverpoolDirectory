package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.databinding.ItemCommentBinding
import com.dsoft.liverpooldirectory.model.Comments

class SocialCommentsRecyclerAdapter: RecyclerView.Adapter<SocialCommentsRecyclerAdapter.ViewHolder>() {

    private var commentsList = emptyList<Comments>()

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

    fun setData(comments: List<Comments>) {
        this.commentsList = comments
        notifyDataSetChanged()
    }
}
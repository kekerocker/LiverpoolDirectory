package com.dsoft.liverpooldirectory2.ui.social.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dsoft.liverpooldirectory2.R
import com.dsoft.liverpooldirectory2.databinding.ItemCommentBinding
import com.dsoft.liverpooldirectory2.model.VKCommentData
import com.dsoft.liverpooldirectory2.utility.RandomName

class SocialCommentsRecyclerAdapter :
    RecyclerView.Adapter<SocialCommentsRecyclerAdapter.ViewHolder>() {

    var commentsList: List<VKCommentData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemUserName: TextView = binding.tvProfileName
        val itemProfilePic: ImageView = binding.ivProfilePic
        val itemText: TextView = binding.tvCommentsText
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SocialCommentsRecyclerAdapter.ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SocialCommentsRecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = commentsList[position]

        holder.itemText.text = currentItem.text

        if(currentItem.firstName == "Unknown") {
            holder.itemUserName.text = RandomName.getRandomName()
        } else {
            holder.itemUserName.text = "${currentItem.firstName} ${currentItem.lastName}"
        }
        if (currentItem.profilePic != "") {
            holder.itemProfilePic.load(currentItem.profilePic) {
                transformations(CircleCropTransformation())
            }
        } else {
            holder.itemProfilePic.load(R.drawable.no_pic) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }
}
package com.example.liverpooldirectory.fragments.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.liverpooldirectory.R

class  RecyclerAdapter(
    private var titles: List<String>,
    private var details: List<String>,
    private var content: List<String>,
    private var images: List<String>,
    private var links: List<String>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemContent: TextView = itemView.findViewById(R.id.tv_content)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)

        init {

            itemView.setOnClickListener {
                val position: Int = adapterPosition

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent, false)
        return ViewHolder((v))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemContent.text = content[position]

        holder.itemPicture.load(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

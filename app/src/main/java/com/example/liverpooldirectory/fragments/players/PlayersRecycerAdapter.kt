package com.example.liverpooldirectory.fragments.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpooldirectory.R

class PlayersRecyclerAdapter(
    private var amplua: List<String>,
    private var firstName: List<String>,
    private var lastName: List<String>,
    private var number: List<Int>,
    private var age: List<Int>
) : RecyclerView.Adapter<PlayersRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPosition: TextView = itemView.findViewById(R.id.player_position)
        val itemNumber: TextView = itemView.findViewById(R.id.player_number)
        val itemFirstName: TextView = itemView.findViewById(R.id.player_firstName)
        val itemLastName: TextView = itemView.findViewById(R.id.player_lastName)
        val itemAge: TextView = itemView.findViewById(R.id.player_age)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_social_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemPosition.text = amplua[position]
        holder.itemNumber.text = number[position].toString()
        holder.itemFirstName.text = firstName[position]
        holder.itemLastName.text = lastName[position]
        holder.itemAge.text = age[position].toString()
    }

    override fun getItemCount(): Int {
        return firstName.size
    }
}


package com.example.liverpooldirectory.fragments.mainmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpooldirectory.R

class  RecyclerAdapterTable(
    private var positions: List<String>,
    private var clubs: List<String>,
    private var games: List<String>,
    private var wins: List<String>,
    private var draws: List<String>,
    private var loses: List<String>,
    private var points: List<String>
) : RecyclerView.Adapter<RecyclerAdapterTable.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPosition: TextView = itemView.findViewById(R.id.tv_position)
        val itemClub: TextView = itemView.findViewById(R.id.tv_club)
        val itemGame: TextView = itemView.findViewById(R.id.tv_matches)
        val itemWin: TextView = itemView.findViewById(R.id.tv_wins)
        val itemDraw: TextView = itemView.findViewById(R.id.tv_draws)
        val itemLose: TextView = itemView.findViewById(R.id.tv_loses)
        val itemPoint: TextView = itemView.findViewById(R.id.tv_points)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_epl_table,parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemPosition.text = positions[position]
        holder.itemClub.text = clubs[position]
        holder.itemGame.text = games[position]
        holder.itemWin.text = wins[position]
        holder.itemDraw.text = draws[position]
        holder.itemLose.text = loses[position]
        holder.itemPoint.text = points[position]
    }

    override fun getItemCount(): Int {
        return 20
    }
}
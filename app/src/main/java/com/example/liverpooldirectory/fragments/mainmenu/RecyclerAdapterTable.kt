package com.example.liverpooldirectory.fragments.mainmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.model.Table
import kotlinx.android.synthetic.main.item_epl_table.view.*

class RecyclerAdapterTable : RecyclerView.Adapter<RecyclerAdapterTable.MyViewHolder>() {

    private var tableList = emptyList<Table>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_epl_table, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = tableList[position]
        holder.itemView.tv_position.text = currentItem.position.toString()
        holder.itemView.tv_club.text = currentItem.clubName
        holder.itemView.tv_matches.text = currentItem.matches
        holder.itemView.tv_wins.text = currentItem.win
        holder.itemView.tv_draws.text = currentItem.draw
        holder.itemView.tv_loses.text = currentItem.lose
        holder.itemView.tv_points.text = currentItem.points
    }

    override fun getItemCount(): Int {
       return tableList.size
    }

    fun setData(table: List<Table>) {
        this.tableList = table
        notifyDataSetChanged()
    }
}
package com.dsoft.liverpooldirectory.fragments.mainmenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.databinding.ItemEplTableBinding
import com.dsoft.liverpooldirectory.model.Table

class RecyclerAdapterTable : RecyclerView.Adapter<RecyclerAdapterTable.MyViewHolder>() {

    private var tableList = emptyList<Table>()

    class MyViewHolder(binding: ItemEplTableBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemPosition: TextView = binding.tvPosition
        val itemClub: TextView = binding.tvClub
        val itemMatches: TextView = binding.tvMatches
        val itemWins: TextView = binding.tvWins
        val itemDraws: TextView = binding.tvDraws
        val itemLoses: TextView = binding.tvLoses
        val itemPoints: TextView = binding.tvPoints
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemEplTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = tableList[position]
        holder.itemPosition.text = currentItem.position.toString()
        holder.itemClub.text = currentItem.clubName
        holder.itemMatches.text = currentItem.matches
        holder.itemWins.text = currentItem.win
        holder.itemDraws.text = currentItem.draw
        holder.itemLoses.text = currentItem.lose
        holder.itemPoints.text = currentItem.points

        when (holder.itemClub.text) {
            "Манчестер Сити" -> holder.itemClub.text = "Ман Сити"
            "Манчестер Юнайтед" -> holder.itemClub.text = "Ман Юнайтед"
        }
    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    fun setData(table: List<Table>) {
        this.tableList = table
        notifyDataSetChanged()
    }
}
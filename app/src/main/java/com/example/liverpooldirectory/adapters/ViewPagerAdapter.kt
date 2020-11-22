package com.example.liverpooldirectory.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.liverpooldirectory.R

class ViewPagerAdapter (
    private var teamName1: List<String>,
    private var teamName2: List<String>,
    private var score_time: List<String>,
    private var date: List<String>,
    private var tournamentLogo: List<String>,
    private var teamLogo1: List<String>,
    private var teamLogo2: List<String>,
    private var matchType: List<String>
) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTeamName1: TextView = itemView.findViewById(R.id.teamName1)
        val itemTeamName2: TextView = itemView.findViewById(R.id.teamName2)
        val itemScoreTime: TextView = itemView.findViewById(R.id.score_time)
        val itemDate: TextView = itemView.findViewById(R.id.date)
        val itemMatchType: TextView = itemView.findViewById(R.id.matchType)
        val itemTournamentLogo: ImageView = itemView.findViewById(R.id.tournamentLogo)
        val itemTeamLogo1: ImageView = itemView.findViewById(R.id.teamlogo1)
        val itemTeamLogo2: ImageView = itemView.findViewById(R.id.teamlogo2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTeamName1.text = teamName1[position]
        holder.itemTeamName2.text = teamName2[position]
        holder.itemScoreTime.text = score_time[position]
        holder.itemDate.text = date[position]
        holder.itemMatchType.text = matchType[position]

        Glide.with(holder.itemTournamentLogo)
            .load(tournamentLogo[position])
            .into(holder.itemTournamentLogo)

        Glide.with(holder.itemTeamLogo1)
            .load(teamLogo1[position])
            .into(holder.itemTeamLogo1)

        Glide.with(holder.itemTeamLogo2)
            .load(teamLogo2[position])
            .into(holder.itemTeamLogo2)
    }

    override fun getItemCount(): Int {
        return teamName1.size
    }
}

package com.dsoft.liverpooldirectory.ui.mainmenu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.databinding.ItemMatchBinding
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.service.GlobalService
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    val globalService = GlobalService()

    private var closeGames = emptyList<CloseGames>()

    lateinit var context: Context

    inner class Pager2ViewHolder(binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTeamName1: TextView = binding.teamName1
        val itemTeamName2: TextView = binding.teamName2
        val itemScoreTime: TextView = binding.scoreTime
        val itemDate: TextView = binding.date
        val itemMatchType: TextView = binding.matchType
        val itemTournamentLogo: ImageView = binding.tournamentLogo
        val itemTeamLogo1: ImageView = binding.teamlogo1
        val itemTeamLogo2: ImageView = binding.teamlogo2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        context = parent.context
        return Pager2ViewHolder(
            ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val currentItem = closeGames[position]
        holder.itemTeamName1.text = currentItem.teamName1
        holder.itemTeamName2.text = currentItem.teamName2
        holder.itemScoreTime.text = currentItem.score
        holder.itemDate.text = convertLongToDate(currentItem.date)
        holder.itemMatchType.text = currentItem.matchType

        holder.itemTournamentLogo.load(currentItem.tournamentLogo)

        if (currentItem.teamName1 == "Бернлис") {
            holder.itemTeamName1.text = "Бернли"
        }

        if (currentItem.teamName2 == "Бернлис") {
            holder.itemTeamName2.text = "Бернли"
        }

        holder.itemTeamLogo1.load(globalService.getLogo(currentItem.teamName1))
        holder.itemTeamLogo2.load(globalService.getLogo(currentItem.teamName2))

    }

    override fun getItemCount(): Int {
        return closeGames.size
    }

    fun setData(closeGames: List<CloseGames>) {
        this.closeGames = closeGames
        notifyDataSetChanged()
    }

    private fun convertLongToDate(rawDate: Long): String {
        val sharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
        val date = Date(rawDate)
        val sdf: SimpleDateFormat = if (sharedPreferences.getString("Language", "") == "ru") {
            SimpleDateFormat("dd MMM yyyy", Locale("ru", "Ru"))
        } else {
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        }
        return sdf.format(date)
    }
}
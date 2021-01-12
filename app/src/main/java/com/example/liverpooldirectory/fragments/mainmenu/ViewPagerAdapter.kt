package com.example.liverpooldirectory.fragments.mainmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.model.CloseGames

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    private var closeGames = emptyList<CloseGames>()

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        val currentItem = closeGames[position]
        holder.itemTeamName1.text = currentItem.teamName1
        holder.itemTeamName2.text = currentItem.teamName2
        holder.itemScoreTime.text = currentItem.score
        holder.itemDate.text = currentItem.date
        holder.itemMatchType.text = currentItem.matchType

        Glide.with(holder.itemTournamentLogo)
            .load(currentItem.tournamentLogo)
            .into(holder.itemTournamentLogo)

        when (holder.itemTeamName1.text) {
            "Ливерпуль" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_liverpool)
            "Манчестер Юнайтед" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_manutd)
            "Лестер" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_leicester)
            "Тоттенхэм" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_tottenham)
            "Манчестер Сити" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_mancity_logo)
            "Саутгемптон" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_southampton)
            "Эвертон" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_everton)
            "Астон Вилла" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_avilla)
            "Челси" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_chelsea)
            "Вест Хэм" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_westhamutd)
            "Арсенал" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_arsenal)
            "Лидс" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_leedsutd)
            "Вулверхэмптон" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_wolverhampton)
            "Кристал Пэлас" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_crystal_palace)
            "Ньюкасл" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_newcastleutd)
            "Бернли" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_burnley)
            "Брайтон" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_brighton)
            "Фулхэм" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_fullham)
            "Вест Бромвич" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_westbrom)
            "Шеффилд Юнайтед" -> holder.itemTeamLogo1.setImageResource(R.drawable.fc_sheffieldutd)
        }
        when (holder.itemTeamName2.text) {
            "Ливерпуль" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_liverpool)
            "Манчестер Юнайтед" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_manutd)
            "Лестер" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_leicester)
            "Тоттенхэм" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_tottenham)
            "Манчестер Сити" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_mancity_logo)
            "Саутгемптон" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_southampton)
            "Эвертон" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_everton)
            "Астон Вилла" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_avilla)
            "Челси" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_chelsea)
            "Вест Хэм" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_westhamutd)
            "Арсенал" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_arsenal)
            "Лидс" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_leedsutd)
            "Вулверхэмптон" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_wolverhampton)
            "Кристал Пэлас" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_crystal_palace)
            "Ньюкасл" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_newcastleutd)
            "Бернли" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_burnley)
            "Брайтон" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_brighton)
            "Фулхэм" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_fullham)
            "Вест Бромвич" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_westbrom)
            "Шеффилд Юнайтед" -> holder.itemTeamLogo2.setImageResource(R.drawable.fc_sheffieldutd)
        }
    }


    override fun getItemCount(): Int {
        return closeGames.size
    }

    fun setData(closeGames: List<CloseGames>) {
        this.closeGames = closeGames
        notifyDataSetChanged()
    }
}
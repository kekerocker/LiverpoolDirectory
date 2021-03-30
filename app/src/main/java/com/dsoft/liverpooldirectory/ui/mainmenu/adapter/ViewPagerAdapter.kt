package com.dsoft.liverpooldirectory.ui.mainmenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.ItemMatchBinding
import com.dsoft.liverpooldirectory.model.CloseGames

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    private var closeGames = emptyList<CloseGames>()

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
        return Pager2ViewHolder(
            ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val currentItem = closeGames[position]
        holder.itemTeamName1.text = currentItem.teamName1
        holder.itemTeamName2.text = currentItem.teamName2
        holder.itemScoreTime.text = currentItem.score
        holder.itemDate.text = currentItem.date
        holder.itemMatchType.text = currentItem.matchType

        holder.itemTournamentLogo.load(currentItem.tournamentLogo)

        when (holder.itemTeamName1.text) {
            "Ливерпуль" -> holder.itemTeamLogo1.load(R.drawable.fc_liverpool)
            "Манчестер Юнайтед" -> holder.itemTeamLogo1.load(R.drawable.fc_manutd)
            "Лестер" -> holder.itemTeamLogo1.load(R.drawable.fc_leicester)
            "Тоттенхэм" -> holder.itemTeamLogo1.load(R.drawable.fc_tottenham)
            "Манчестер Сити" -> holder.itemTeamLogo1.load(R.drawable.fc_mancity_logo)
            "Саутгемптон" -> holder.itemTeamLogo1.load(R.drawable.fc_southampton)
            "Эвертон" -> holder.itemTeamLogo1.load(R.drawable.fc_everton)
            "Астон Вилла" -> holder.itemTeamLogo1.load(R.drawable.fc_avilla)
            "Челси" -> holder.itemTeamLogo1.load(R.drawable.fc_chelsea)
            "Вест Хэм" -> holder.itemTeamLogo1.load(R.drawable.fc_westhamutd)
            "Арсенал" -> holder.itemTeamLogo1.load(R.drawable.fc_arsenal)
            "Лидс" -> holder.itemTeamLogo1.load(R.drawable.fc_leedsutd)
            "Вулверхэмптон" -> holder.itemTeamLogo1.load(R.drawable.fc_wolverhampton)
            "Кристал Пэлас" -> holder.itemTeamLogo1.load(R.drawable.fc_crystal_palace)
            "Ньюкасл" -> holder.itemTeamLogo1.load(R.drawable.fc_newcastleutd)
            "Бернли" -> holder.itemTeamLogo1.load(R.drawable.fc_burnley)
            "Брайтон" -> holder.itemTeamLogo1.load(R.drawable.fc_brighton)
            "Фулхэм" -> holder.itemTeamLogo1.load(R.drawable.fc_fullham)
            "Вест Бромвич" -> holder.itemTeamLogo1.load(R.drawable.fc_westbrom)
            "Шеффилд Юнайтед" -> holder.itemTeamLogo1.load(R.drawable.fc_sheffieldutd)
            "РБ Лейпциг" -> holder.itemTeamLogo1.load(R.drawable.fc_rb_leipzieg)
            "Барселона" -> holder.itemTeamLogo1.load(R.drawable.fc_barcelona)
            "ПСЖ" -> holder.itemTeamLogo1.load(R.drawable.fc_psg)
            "Реал Мадрид" -> holder.itemTeamLogo1.load(R.drawable.fc_rm)
        }

        when (holder.itemTeamName2.text) {
            "Ливерпуль" -> holder.itemTeamLogo2.load(R.drawable.fc_liverpool)
            "Манчестер Юнайтед" -> holder.itemTeamLogo2.load(R.drawable.fc_manutd)
            "Лестер" -> holder.itemTeamLogo2.load(R.drawable.fc_leicester)
            "Тоттенхэм" -> holder.itemTeamLogo2.load(R.drawable.fc_tottenham)
            "Манчестер Сити" -> holder.itemTeamLogo2.load(R.drawable.fc_mancity_logo)
            "Саутгемптон" -> holder.itemTeamLogo2.load(R.drawable.fc_southampton)
            "Эвертон" -> holder.itemTeamLogo2.load(R.drawable.fc_everton)
            "Астон Вилла" -> holder.itemTeamLogo2.load(R.drawable.fc_avilla)
            "Челси" -> holder.itemTeamLogo2.load(R.drawable.fc_chelsea)
            "Вест Хэм" -> holder.itemTeamLogo2.load(R.drawable.fc_westhamutd)
            "Арсенал" -> holder.itemTeamLogo2.load(R.drawable.fc_arsenal)
            "Лидс" -> holder.itemTeamLogo2.load(R.drawable.fc_leedsutd)
            "Вулверхэмптон" -> holder.itemTeamLogo2.load(R.drawable.fc_wolverhampton)
            "Кристал Пэлас" -> holder.itemTeamLogo2.load(R.drawable.fc_crystal_palace)
            "Ньюкасл" -> holder.itemTeamLogo2.load(R.drawable.fc_newcastleutd)
            "Бернли" -> holder.itemTeamLogo2.load(R.drawable.fc_burnley)
            "Брайтон" -> holder.itemTeamLogo2.load(R.drawable.fc_brighton)
            "Фулхэм" -> holder.itemTeamLogo2.load(R.drawable.fc_fullham)
            "Вест Бромвич" -> holder.itemTeamLogo2.load(R.drawable.fc_westbrom)
            "Шеффилд Юнайтед" -> holder.itemTeamLogo2.load(R.drawable.fc_sheffieldutd)
            "РБ Лейпциг" -> holder.itemTeamLogo2.load(R.drawable.fc_rb_leipzieg)
            "Барселона" -> holder.itemTeamLogo2.load(R.drawable.fc_barcelona)
            "ПСЖ" -> holder.itemTeamLogo2.load(R.drawable.fc_psg)
            "Реал Мадрид" -> holder.itemTeamLogo2.load(R.drawable.fc_rm)
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
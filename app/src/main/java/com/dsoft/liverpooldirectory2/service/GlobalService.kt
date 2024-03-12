package com.dsoft.liverpooldirectory2.service

import com.dsoft.liverpooldirectory2.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalService @Inject constructor() {

    fun getLogo(teamName: String): Int {
        return when (teamName) {
            "Ливерпуль" -> R.drawable.fc_liverpool
            "Манчестер Юнайтед" -> R.drawable.fc_manutd
            "МЮ" -> R.drawable.fc_manutd
            "Лестер" -> R.drawable.fc_leicester
            "Тоттенхэм" -> R.drawable.fc_tottenham
            "Манчестер Сити" -> R.drawable.fc_mancity_logo
            "Саутгемптон" -> R.drawable.fc_southampton
            "Эвертон" -> R.drawable.fc_everton
            "Астон Вилла" -> R.drawable.fc_avilla
            "Челси" -> R.drawable.fc_chelsea
            "Вест Хэм" -> R.drawable.fc_westhamutd
            "Арсенал" -> R.drawable.fc_arsenal
            "Лидс" -> R.drawable.fc_leedsutd
            "Вулверхэмптон" -> R.drawable.fc_wolverhampton
            "Кристал Пэлас" -> R.drawable.fc_crystal_palace
            "Ньюкасл" -> R.drawable.fc_newcastleutd
            "Бернли" -> R.drawable.fc_burnley
            "Бернлис" -> R.drawable.fc_burnley
            "Брайтон" -> R.drawable.fc_brighton
            "Фулхэм" -> R.drawable.fc_fullham
            "Вест Бромвич" -> R.drawable.fc_westbrom
            "Шеффилд Юнайтед" -> R.drawable.fc_sheffieldutd
            "РБ Лейпциг" -> R.drawable.fc_rb_leipzieg
            "Барселона" -> R.drawable.fc_barcelona
            "ПСЖ" -> R.drawable.fc_psg
            "Реал Мадрид" -> R.drawable.fc_rm
            "Норвич" -> R.drawable.fc_norwich
            else -> R.drawable.fc_no_logo
        }
    }

}
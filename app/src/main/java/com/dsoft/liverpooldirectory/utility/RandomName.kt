package com.dsoft.liverpooldirectory.utility

object RandomName {

    private val listName = listOf("Сергей", "Андрей", "Роман", "Антон", "Владислав", "Асхаб", "Одинокий", "Бухарик", "Олег", "Марат", "Гусейн", "Павел")
    private val listLastName = listOf("Сергеев", "Андреев", "Романов", "Антонов", "Валдайский", "Асхабов", "Пташинский", "Бухарик", "Кузин", "Новиков", "Рогушкин", "Катаржанов", "Макаров")

    fun getRandomName(): String {
        val randomName = (0..listName.size).shuffled().first()
        val randomLastName = (0..listLastName.size).shuffled().first()

        return "${listName[randomName]} ${listLastName[randomLastName]}"
    }

}
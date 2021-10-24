package com.dsoft.liverpooldirectory.repository

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class MainMenuRepositoryTest {

    @Test
    fun `convert long to date`() {
        val time = 1635064004092 // 24 октября 2021 11:26
        val sdf = SimpleDateFormat("dd MMMMM yyyy hh:mm", Locale("ru"))
        val formattedTime = sdf.format(time)

        assertThat(formattedTime).isEqualTo("24 октября 2021 11:26")
    }

}
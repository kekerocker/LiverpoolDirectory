package com.dsoft.liverpooldirectory2

import com.dsoft.liverpooldirectory2.model.TableData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `download data from internet and store it in list`() {
        val list = mutableListOf<TableData>()
        val response = TableData(1, "LFC", "4", "3", "1", "0", "10")

        list.add(response)
        assertThat(list).contains(response)
    }
}

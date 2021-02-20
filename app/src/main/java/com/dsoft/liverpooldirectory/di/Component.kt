package com.dsoft.liverpooldirectory.di

import com.dsoft.liverpooldirectory.internet.VKInfo
import dagger.Component

@Component
interface Component {

    fun getVkInfo(): VKInfo

}
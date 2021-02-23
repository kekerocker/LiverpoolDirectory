package com.dsoft.liverpooldirectory.di

import com.dsoft.liverpooldirectory.internet.Retrofit
import dagger.Component

@Component
interface Component {

    fun getRetrofit(): Retrofit

}
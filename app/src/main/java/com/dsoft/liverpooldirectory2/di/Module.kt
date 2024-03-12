package com.dsoft.liverpooldirectory2.di

import android.content.Context
import androidx.room.Room
import com.dsoft.liverpooldirectory2.data.AppPreferences
import com.dsoft.liverpooldirectory2.data.LFCDatabase
import com.dsoft.liverpooldirectory2.data.api.VKAPIRequest
import com.dsoft.liverpooldirectory2.other.Constants.DATABASE_NAME
import com.dsoft.liverpooldirectory2.other.Constants.VK_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(VK_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideVkApi(retrofit: Retrofit): VKAPIRequest {
            return retrofit.create(VKAPIRequest::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            LFCDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideNewsDao(db: LFCDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideTableDao(db: LFCDatabase) = db.tableDao()

    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }

}
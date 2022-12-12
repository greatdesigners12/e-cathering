package com.training.e_cathering.di

import com.training.e_cathering.Constant.BASE_URL
import com.training.e_cathering.network.AuthAPI
import com.training.e_cathering.network.CatheringAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideCatheringAPI() : CatheringAPI {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CatheringAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAPI() : AuthAPI {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(AuthAPI::class.java)
    }
}
package com.example.usertestapplication.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClass {

    val loging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providerRetrofitClient(): Retrofit {
        return Retrofit.Builder().client(providerOkHttpClient())
            .baseUrl("https://fake-json-api.mock.beeceptor.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun providerOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loging).build()
    }

    @Provides
    fun providerApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
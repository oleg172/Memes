package com.olmi.android.memes.data.service

import com.olmi.android.memes.data.BASE_URL
import com.olmi.android.memes.data.api.MemesApi
import com.olmi.android.memes.data.handler.ErrorHandlingCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    companion object {

        fun getApi(): MemesApi {
            return getInstance().create(MemesApi::class.java)
        }

        private fun getInstance(): Retrofit {
            val logInteractor = HttpLoggingInterceptor()
            logInteractor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(logInteractor).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ErrorHandlingCallAdapterFactory.create())
                .build()
        }
    }
}

package com.example.dictionarycardswipe.di

import android.content.Context
import androidx.room.Room
import com.example.dictionarycardswipe.api.ApiDetails
import com.example.dictionarycardswipe.api.ApiReference
import com.example.dictionarycardswipe.repo.RepoRemote
import com.example.dictionarycardswipe.repo.RepoInterface
import com.example.dictionarycardswipe.room.AcronymDao
import com.example.dictionarycardswipe.room.AcronymDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    @Provides
    fun retrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiReference.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun getApiDetails(retrofit: Retrofit): ApiDetails {
        return retrofit.create(ApiDetails::class.java)
    }

    @Provides
    fun getRepo(myApiDetails: ApiDetails): RepoInterface {
        return RepoRemote(myApiDetails)
    }

    @Provides
    fun provideAcronymDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AcronymDatabase::class.java,
        "AcronymDatabase",
    ).build()

    @Provides
    fun provideDao(database: AcronymDatabase) = database.acronymDao()
}
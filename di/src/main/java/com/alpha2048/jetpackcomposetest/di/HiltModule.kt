package com.alpha2048.jetpackcomposetest.di

import com.alpha2048.jetpackcomposetest.data.api.GithubApiInterface
import com.alpha2048.jetpackcomposetest.data.repository.SearchRepositoryImpl
import com.alpha2048.jetpackcomposetest.domain.repository.SearchRepository
import com.alpha2048.jetpackcomposetest.domain.usecase.SearchRepositoryUseCaseImpl
import com.alpha2048.jetpackcomposetest.presentation.usecase.SearchRepositoryUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
abstract class AppModule {


    // ----------------- repository -----------------

    @Singleton
    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): com.alpha2048.jetpackcomposetest.domain.repository.SearchRepository

    // ----------------- usecase -----------------

    @Singleton
    @Binds
    abstract fun bindGetRepositoryUseCase(getRepositoryUseCaseImpl: com.alpha2048.jetpackcomposetest.domain.usecase.SearchRepositoryUseCaseImpl): SearchRepositoryUseCase
}

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(okHttpClient: OkHttpClient): GithubApiInterface {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        return retrofit.create(GithubApiInterface::class.java)
    }
}


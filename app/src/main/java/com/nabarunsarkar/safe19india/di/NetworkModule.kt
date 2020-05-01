package com.nabarunsarkar.safe19india.di

import android.content.Context
import com.nabarunsarkar.safe19india.api.*
import com.nabarunsarkar.safe19india.repository.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.nabarunsarkar.safe19india.utils.isNetworkAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Safe19IndiaApiService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(Safe19IndiaApiService::class.java)
    }

    single {
        SafeIndiaRepository(get())
    }
}

@ExperimentalCoroutinesApi
val networkModuleDistrict = module {
    single {
        Retrofit.Builder()
            .baseUrl(DistrictApiService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(DistrictApiService::class.java)
    }

    single {
        DistrictRepository(get())
    }
}

@ExperimentalCoroutinesApi
val networkModuleEssential = module {
    single {
        Retrofit.Builder()
            .baseUrl(EssentialAPIService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(EssentialAPIService::class.java)
    }

    single {
        EssentialRepository(get())
    }
}

@ExperimentalCoroutinesApi
val networkModuleAppList = module {
    single {
        Retrofit.Builder()
            .baseUrl(AppListAPIService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(AppListAPIService::class.java)
    }

    single {
        AppListRepository(get())
    }
}

@ExperimentalCoroutinesApi
val networkModuleFaq = module {
    single {
        Retrofit.Builder()
            .baseUrl(FaqAPIService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(FaqAPIService::class.java)
    }

    single {
        SafeFaqRepository(get())
    }
}

fun getOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (isNetworkAvailable(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            chain.proceed(request)
        }
        .build()
}
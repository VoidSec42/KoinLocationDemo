package com.example.koindemo

import com.example.koindemo.api.Api
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.sunrise-sunset.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    single<MainRepositoryInterface> {
        MainRepository(get(), androidContext())
    }

}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}
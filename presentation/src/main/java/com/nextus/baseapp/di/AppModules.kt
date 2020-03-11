package com.nextus.baseapp.di

import com.nextus.baseapp.ui.main.MainViewModel
import com.nextus.data.repository.GistsRepositoryImpl
import com.nextus.data.source.remote.GistsRemoteDataSource
import com.nextus.data.network.RemoteClient
import com.nextus.data.network.api.GistsApi
import com.nextus.data.source.remote.GistsRemoteDataSourceImpl
import com.nextus.domain.repository.GistsRepository
import com.nextus.domain.usecase.GetGistListUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val apiModule = module {
    single { gistsApi }
}

val dataSourceModule = module {
    single { GistsRemoteDataSourceImpl(get()) as GistsRemoteDataSource }
}

val dataRepositoryModule = module {
    single { GistsRepositoryImpl(get()) as GistsRepository }
}

val useCaseModule = module {
    single { GetGistListUseCase(get()) }
}

private val retrofit = RemoteClient().createRetrofit(true)

private val gistsApi = retrofit.create(GistsApi::class.java)

val appModules = listOf(
    viewModelModule,
    apiModule,
    dataSourceModule,
    dataRepositoryModule,
    useCaseModule
)
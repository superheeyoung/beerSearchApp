package com.example.beersearchapp.di

import com.example.beersearchapp.data.mapper.BeerEntityMapper
import com.example.beersearchapp.data.repository.*
import com.example.beersearchapp.domain.repository.BeerRepository
import com.example.beersearchapp.domain.usecase.BeerUseCase
import com.example.beersearchapp.presentation.mapper.BeerModelMapper
import com.example.beersearchapp.presentation.viewmodel.BeerListMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val remoteSourceModule = module {
    single<BeerRemoteSource> { BeerRemoteSourceImpl(get())}
}

val dataSourceModule = module {
    single<BeerDataSource> { BeerDataSourceimpl(get())}
}

val repositoryModule = module {
    single<BeerRepository> { BeerRepositoryImpl(get(), get())}
}

val mapperModule = module {
    factory { BeerEntityMapper()}
    factory { BeerModelMapper()}
}

val useCaseModule = module {
    factory { BeerUseCase(get())}
}

val viewModelModule = module {
    viewModel { BeerListMainViewModel(get(), get()) }
}

val appModule = listOf(
    retrofitModule,
    remoteSourceModule,
    dataSourceModule,
    repositoryModule,
    mapperModule,
    useCaseModule,
    viewModelModule
)


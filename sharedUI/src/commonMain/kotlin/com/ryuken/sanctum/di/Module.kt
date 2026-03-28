package com.ryuken.sanctum.di


import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

// commonMain/di/AppModule.kt
val Module = module {
    // Network
    single  { createHttpClient() }
    single  { FoodApiService(get()) }

    // Database
    single  { DatabaseDriverFactory(get()) }
    single  { createDatabase(get()) }

    // Repositories
    single  { FoodLogRepository(get()) }
    single  { FoodCacheRepository(get()) }
    single  { UserPreferencesRepository(get()) }

    // ViewModels
    viewModel { DiaryViewModel(get(), get()) }
    viewModel { FoodSearchViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
}

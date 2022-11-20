package com.capstone.hewankita.di

import android.content.Context
import com.capstone.hewankita.data.AnimalRepository
import com.capstone.hewankita.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): AnimalRepository {
        val apiService = ApiConfig.getApiService()
        return AnimalRepository.getInstance(apiService)
    }
}
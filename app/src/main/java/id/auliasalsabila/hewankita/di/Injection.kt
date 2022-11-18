package id.auliasalsabila.hewankita.di

import android.content.Context
import id.auliasalsabila.hewankita.data.AnimalRepository
import id.auliasalsabila.hewankita.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): AnimalRepository {
        val apiService = ApiConfig.getApiService()
        return AnimalRepository.getInstance(apiService)
    }
}
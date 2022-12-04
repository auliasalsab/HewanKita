package com.capstone.hewankita.data

import com.capstone.hewankita.data.remote.api.ApiService
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AnimalRepository(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: AnimalRepository? = null

        fun getInstance(
            apiService: ApiService
        ): AnimalRepository =
            instance ?: synchronized(this) {
                instance ?: AnimalRepository(apiService)
            }.also { instance = it }
    }
}
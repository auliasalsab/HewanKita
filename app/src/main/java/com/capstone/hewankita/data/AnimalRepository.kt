package com.capstone.hewankita.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.hewankita.data.remote.api.ApiService
import com.capstone.hewankita.data.remote.response.LoginResult
import com.capstone.hewankita.data.remote.response.AllResponse

class AnimalRepository(private val apiService: ApiService) {

    fun login(email: String, password: String): LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response.loginResult))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d(TAG, "onFailure: ${e.message.toString()}")
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        val registerResponse = MutableLiveData<AllResponse>()
        try {
            val response = apiService.register(name, email, password)
            registerResponse.postValue(response)
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

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
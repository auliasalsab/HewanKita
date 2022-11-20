package com.capstone.hewankita.ui.register


import androidx.lifecycle.ViewModel
import com.capstone.hewankita.data.AnimalRepository

class RegisterViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = animalRepository.register(name, email, password)
}

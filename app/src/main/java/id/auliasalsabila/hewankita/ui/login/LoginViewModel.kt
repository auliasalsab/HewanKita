package id.auliasalsabila.hewankita.ui.login

import androidx.lifecycle.*
import id.auliasalsabila.hewankita.data.AnimalRepository

class LoginViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    fun login(email: String, password: String) = animalRepository.login(email, password)
}

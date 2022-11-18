package id.auliasalsabila.hewankita.ui.register


import androidx.lifecycle.ViewModel
import id.auliasalsabila.hewankita.data.AnimalRepository

class RegisterViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = animalRepository.register(name, email, password)
}

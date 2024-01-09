package es.unex.asee.mercapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.asee.mercapp.MercApplication
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.User
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: Repository
): ViewModel(){

    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    var userInSession: User? = null
        set(value) {
            field = value
            _user.value = value!!
        }

    private val _previousCategories = MutableLiveData<List<GenericCategory>>()
    val previousCategories: LiveData<List<GenericCategory>>
        get() = _previousCategories


    fun updatePreviousCategories() {
        viewModelScope.launch {
            _previousCategories.value = repository.fetchSuperCategories()
        }
    }

    private val _navigateToProducts = MutableLiveData<GenericCategory>(null)
    val navigateToProducts: LiveData<GenericCategory>
        get() = _navigateToProducts

    fun onCategoryClick(category: GenericCategory) {
        _navigateToProducts.value = category
    }

    companion object {
        private const val TAG = "HomeViewModel"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return HomeViewModel( (application as MercApplication).appContainer.repository) as T
            }
        }
    }

}


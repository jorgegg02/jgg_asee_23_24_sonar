package es.unex.asee.mercapp.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.asee.mercapp.MercApplication
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.data.model.GenericCategory
import kotlinx.coroutines.launch

class CatalogViewModel (
    private val repository: Repository
): ViewModel() {


    private lateinit var homeViewModel: HomeViewModel

    fun setHomeViewModel(homeViewModel: HomeViewModel) {
        this.homeViewModel = homeViewModel
    }

    private val _categories = MutableLiveData<List<GenericCategory>>()
    val categories: LiveData<List<GenericCategory>>
        get() = _categories



    // Método para actualizar las categorías según la selección del usuario
    fun updateCategories(newCategories: List<GenericCategory>) {
        _categories.value = newCategories
    }

    fun init() {

        refresh()
        viewModelScope.launch {
            _categories.value = repository.fetchSuperCategories()
        }
    }

     fun refresh() {
        viewModelScope.launch { repository.apiFetchSuperCategories() }
    }

    fun handleCategoryAction(category: GenericCategory) {

        Log.v("CatalogViewModel", "handleCategoryAction: ${category.GenCatName}")
        viewModelScope.launch {

            if (category.GenCatLevel == 1 ) {
                // Si es una categoría de nivel 1, se actualizan las supercategorías a categorias
                updateCategories(repository.fetchCategories(category))
            } else if (category.GenCatLevel == 2) {

                // Si es una categoría de nivel 2, se actualizan las categorías a subcategorías
                updateCategories(repository.fetchSubCategories(category))
            }
            else {
                // Si es una categoría de nivel 3, se navega a la pantalla de productos
                homeViewModel.onCategoryClick(category)

            }
        }
    }

    fun handleBackAction() {
        Log.v("CatalogViewModel", "handleBackAction")
        Log.v("CatalogViewModel", categories.value.toString())
        Log.v("CatalogViewModel", categories.value!![0].toString())
        viewModelScope.launch {
            if (categories.value!![0].GenCatLevel == 2) {
                // Si es una categoría de nivel 2, se actualizan las categorías a supercategorías
                updateCategories(repository.fetchSuperCategories())
            } else if (categories.value!![0].GenCatLevel == 3) {
                // Si es una categoría de nivel 3, se actualizan las subcategorías a categorías

                var subCategory = categories.value!![0]
                Log.v("CatalogViewModel", "handleBackAction1: ${subCategory.toString()}")
                var category = repository.fetchCategoryFromSubcategory(subCategory)
                Log.v("CatalogViewModel", "handleBackAction2: ${category.toString()}")
                var superCategory = repository.fetchSuperCategoryFromCategory(category)
                Log.v("CatalogViewModel", "handleBackAction3: ${superCategory.toString()}")
                var categories = repository.fetchCategories(superCategory)
                Log.v("CatalogViewModel", "handleBackAction4: ${categories.toString()}")
                updateCategories(categories)

//                updateCategories(repository.fetchCategories(
//                    repository.fetchSuperCategoryFromCategory(
//                        repository.fetchCategoryFromSubcategory(categories.value!![0])
//                    )
//                )
//                )
            }
            else {
                // Si es una categoría de nivel 1, se navega a la pantalla de inicio
            }
        }
    }


    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return CatalogViewModel( (application as MercApplication).appContainer.repository) as T
            }
        }
    }

}
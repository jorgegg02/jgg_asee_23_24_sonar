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
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.data.model.User
import kotlinx.coroutines.launch

class ProductViewModel (
    private val repository: Repository
): ViewModel() {

    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId!!)
        }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    private val _subcategory = MutableLiveData<GenericCategory>(null)
    val subcategory: LiveData<GenericCategory>
        get() = _subcategory

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    fun setSubcategory(subcategory: GenericCategory) {
        _subcategory.value = subcategory
        viewModelScope.launch {
            _products.value = repository.fetchProducts(subcategory)
            Log.v("ProductViewModel", "setSubcategory: subcategory ${subcategory}")
            Log.v("ProductViewModel", "setSubcategory:products ${_products.value}")
        }

    }

    fun setToCart(product: Product){
        viewModelScope.launch {
            product.isInCart = true
            repository.addProductToCart(product, user!!.userId!!)
        }
    }

    companion object{

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return ProductViewModel( (application as MercApplication).appContainer.repository) as T
            }
        }
    }
}
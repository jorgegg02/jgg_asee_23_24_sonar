package es.unex.asee.mercapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.asee.mercapp.MercApplication
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.data.model.User
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: Repository
) : ViewModel() {

    val productsInCart = repository.productsInCart

    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId!!)
        }


    fun UnSetToCart(product: Product){
        viewModelScope.launch {
            product.isInCart = false
            repository.deleteProductFromCart(product,user!!.userId!!)
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

                return CartViewModel(
                    (application as MercApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}
package es.unex.asee.mercapp.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.asee.mercapp.MercApplication
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.data.model.User
import es.unex.asee.mercapp.util.CredentialCheck
import kotlinx.coroutines.launch

class SignupViewModel ( private val repository: Repository) : ViewModel() {

    var user: User? = null

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast




    fun onToastShown() {
        _toast.value = null
    }



    private fun notifyInvalidCredentials(msg: String) {
        _toast.value = msg
    }

    private fun navigateBackWithResult(user: User, activity: Activity) {
        with (activity) {
            val intent = Intent().apply {
                putExtra(USERNAME, user.name)
                putExtra(PASS, user.password)
            }
            setResult(AppCompatActivity.RESULT_OK, intent)
            finish()
        }
    }

     fun signup( username: String, password: String, repassword: String , activity: Activity) {

            val check = CredentialCheck.signup(username, password, repassword )
            if (check.fail) notifyInvalidCredentials(check.msg)
            else {
                viewModelScope.launch{
                    val user = User(
                        null,
                        username,
                        password
                    )
                    val id =  repository.insertUser(user)

                    navigateBackWithResult(User(
                            id,
                            username,
                            password
                        ), activity
                    )
                }


        }
    }

    companion object {

        const val USERNAME = "JOIN_USERNAME"
        const val PASS = "JOIN_PASS"

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return SignupViewModel(
                    (application as MercApplication).appContainer.repository,
                ) as T
            }
        }

    }
}
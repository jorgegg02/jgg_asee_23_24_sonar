package es.unex.asee.mercapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.asee.mercapp.MercApplication
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.data.model.User
import es.unex.asee.mercapp.util.CredentialCheck
import es.unex.asee.mercapp.view.home.HomeActivity
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository, private val  appContext: Context ) : ViewModel(){

    var user: User? = null

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    private val _otherActivity = MutableLiveData<Class<*>?>()
    val otherActivity: LiveData<Class<*>?>
        get() = _otherActivity


    fun navigateToWebsite(activity: Activity) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tienda.mercadona.es/"))
        activity.startActivity(webIntent)
    }


    fun login(username: String, password: String){
        checkLogin(username, password)
    }


    private fun checkLogin(username: String, password: String){
        val check = CredentialCheck.login(username, password)
        if (!check.fail){
            viewModelScope.launch{
                val _user = repository.fetchUserByName(username)

                if (_user.userId != null) {
                    // db.userDao().insert(User(-1, etUsername.text.toString(), etPassword.text.toString()))
                    val check = CredentialCheck.passwordOk(password, _user.password)
                    if (check.fail) notifyInvalidCredentials(check.msg)
                    else {user = _user
                        _otherActivity.value= HomeActivity::class.java}

                }
                else notifyInvalidCredentials("Invalid username")
            }
        }
        else notifyInvalidCredentials(check.msg)
    }


    fun onToastShown() {
        _toast.value = null
    }

    fun onOtherActivityShown() {
        _otherActivity.value = null
    }

    private fun notifyInvalidCredentials(msg: String) {
        _toast.value = msg
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

                return LoginViewModel(
                    (application as MercApplication).appContainer.repository,
                    (application as MercApplication).applicationContext
                    ) as T
            }
        }
    }
}
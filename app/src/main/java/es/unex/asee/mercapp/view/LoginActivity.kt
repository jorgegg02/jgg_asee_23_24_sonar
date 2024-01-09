package es.unex.asee.mercapp.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.asee.mercapp.data.model.User
import es.unex.asee.mercapp.database.MercappDatabase
import es.unex.asee.mercapp.util.CredentialCheck
import es.unex.asee.mercapp.view.home.HomeActivity
import kotlinx.coroutines.launch

import es.unex.asee.mercapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }


    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                with(result.data) {
                    val name = this?.getStringExtra(SignupViewModel.USERNAME).orEmpty()
                    val password = this?.getStringExtra(SignupViewModel.PASS).orEmpty()

                    with(binding) {
                        etPassword.setText(password)
                        etUsername.setText(name)
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        "New user ($name/$password) created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //views initialization and listeners
        setUpUI()
        setUpListeners()

        readSettings()
    }

    private fun readSettings(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all

        val rememberme = preferences["rememberme"] as Boolean? ?: false
        val username = preferences["username"] as String? ?: ""
        val password = preferences["password"] as String? ?: ""

        if (rememberme) {
            binding.etUsername.setText(username)
            binding.etPassword.setText(password)
        }
    }

    private fun setUpUI() {

        viewModel.toast.observe(this  ) { text ->
            text?.let {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        viewModel.otherActivity.observe(this) { activity ->
            activity?.let {
                HomeActivity.start(this, viewModel.user!!)
                viewModel.onOtherActivityShown()
            }
        }
    }



    private fun setUpListeners() {
        with(binding) {

            btLogin.setOnClickListener {
                viewModel.login(etUsername.text.toString(), etPassword.text.toString())
            }

            btRegister.setOnClickListener {
                navigateToJoin()
            }
        }

            binding.btWebsiteLink.setOnClickListener {
                viewModel.navigateToWebsite(this )
            }
    }

    private fun navigateToJoin() {
        SignupActivity.start(this, responseLauncher)
    }

}
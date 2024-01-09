package es.unex.asee.mercapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.unex.asee.mercapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private val viewModel: SignupViewModel by viewModels { SignupViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //views initialization and listeners
        setUpUI()
        setUpListeners()
    }


    private fun setUpUI() {
        //get attributes from xml using binding

        viewModel.toast.observe(this) { text ->
            text?.let {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

    }

    private fun setUpListeners() {

            binding.btRegister.setOnClickListener {
                viewModel.signup(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etRepassword.text.toString(),
                    this as Activity
                )
            }

    }



    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, SignupActivity::class.java)
            responseLauncher.launch(intent)
        }
    }

}
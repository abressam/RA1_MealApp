package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.example.ra1_somativa.databinding.ActivityLoginBinding
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.presentation.UserViewModel
import com.example.ra1_somativa.feature.presentation.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            SignIn(email, password)
        }

        binding.goToSignUpButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        updateButtonState()

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val username = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        binding.loginButton.isEnabled = username.isNotBlank() && password.isNotBlank()
    }

    private fun SignIn(email: String, password: String) {

        userViewModel.getUserByEmail(email).observe(this) { user ->
            if (user != null && user.password == password) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setMessage("The username and/or password are incorrect!")
                    .setTitle("Error: invalid credentials")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        }
    }
}
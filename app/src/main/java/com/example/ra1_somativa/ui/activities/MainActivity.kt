package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.ra1_somativa.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import androidx.activity.viewModels
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.User
import com.example.ra1_somativa.feature.presentation.UserViewModel
import com.example.ra1_somativa.feature.presentation.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.confirmPasswordEditText.addTextChangedListener(textWatcher)

        binding.registerButton.setOnClickListener {
            registerUser()
        }

        binding.goToLoginButton.setOnClickListener {
            goToLoginView()
        }

        updateButtonState()
    }

    private fun registerUser() {
        val name = binding.usernameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        userViewModel.getUserByEmail(email).observe(this) { existingUser ->
            if (existingUser != null) {
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("O usuário já existe")
                    .setPositiveButton("OK", null)
                    .show()

                Log.d("MainActivity", "Usuário já existe na base: ${existingUser.name}, ${existingUser.email}")
            } else {
                val newUser = User(
                    name = name,
                    email = email,
                    password = password
                )

                userViewModel.insert(newUser)

                AlertDialog.Builder(this)
                    .setTitle("Bem-vindo")
                    .setMessage("O usuário foi cadastrado com sucesso!")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.cancel()

                        goToLoginView()
                    }
                    .show()
                Log.d("MainActivity", "Novo usuário cadastrado: $name, $email")
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        val allFieldsFilled = username.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        val passwordsMatch = password == confirmPassword

        binding.registerButton.isEnabled = allFieldsFilled && passwordsMatch
    }

    private fun goToLoginView() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
package com.example.parkirkuy.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var errorMessageTextView: TextView
    private lateinit var eyeIcon: ImageButton
    private lateinit var loginButton: ImageButton
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)
        emailEditText = findViewById(R.id.lp_inputemaillogin)
        passwordEditText = findViewById(R.id.lp_inputpasswordlogin)
        errorMessageTextView = findViewById(R.id.error_message)
        eyeIcon = findViewById(R.id.lp_eyeicon)
        loginButton = findViewById(R.id.login_button)
        emailTextInputLayout = findViewById(R.id.emaillogin)
        passwordTextInputLayout = findViewById(R.id.passwordlogin)

        var isPasswordVisible = false

        eyeIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                eyeIcon.setImageResource(R.drawable.view)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                eyeIcon.setImageResource(R.drawable.hide)
            }

            passwordEditText.setSelection(passwordEditText.text.length)
        }

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    emailTextInputLayout.setBackgroundResource(R.drawable.edittextbkgbefore)  // Perbaiki disini
                } else {
                    emailTextInputLayout.setBackgroundResource(R.drawable.edittextbkg)
                }
            }
        })


        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    passwordTextInputLayout.setBackgroundResource(R.drawable.edittextbkgbefore)
                } else {
                    passwordTextInputLayout.setBackgroundResource(R.drawable.edittextbkg)
                }
            }
        })

        loginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        errorMessageTextView.text = ""

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            errorMessageTextView.text = "*Email atau password wajib diisi"
            return
        }

        if (email == "user@example.com" && password == "password123") {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, DetailKendaraanActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            errorMessageTextView.text = "*Email atau Password salah"
        }
    }
}

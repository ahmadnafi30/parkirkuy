package com.example.parkirkuy.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R
import com.google.android.material.textfield.TextInputLayout

class DetailKendaraanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailkendaraanpage)

        val colorInputLayout = findViewById<TextInputLayout>(R.id.dk_colorcontainerr)
        val colorEditText = findViewById<EditText>(R.id.dk_inputcolor)

        val platNomorInputLayout = findViewById<TextInputLayout>(R.id.platnomorcontainer)
        val platNomorEditText = findViewById<EditText>(R.id.lp_inputplatnomor)

        val saveButton = findViewById<ImageButton>(R.id.dk_save_button)

        val leftVehicleButton = findViewById<ImageButton>(R.id.dk_buttonleftvehicle)
        val rightVehicleButton = findViewById<ImageButton>(R.id.dk_buttonrightvehicle)

        val leftVehicleImageButton = findViewById<ImageButton>(R.id.dk_imageleftvehicle)
        val rightVehicleImageButton = findViewById<ImageButton>(R.id.dk_imagerightvehicle)

        // Set up button click listeners
        saveButton.setOnClickListener {
            if (isValidInputs(colorEditText.text.toString(), platNomorEditText.text.toString())) {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }

        leftVehicleButton.setOnClickListener {
            leftVehicleButton.setImageResource(R.drawable.dkbuttonleftafter)
            rightVehicleButton.setImageResource(R.drawable.dkbuttonrightafternotchoose)
        }

        leftVehicleImageButton.setOnClickListener {
            leftVehicleButton.setImageResource(R.drawable.dkbuttonleftafter)
            rightVehicleButton.setImageResource(R.drawable.dkbuttonrightafternotchoose)
        }

        rightVehicleButton.setOnClickListener {
            leftVehicleButton.setImageResource(R.drawable.dkbuttonleftafternotchoose)
            rightVehicleButton.setImageResource(R.drawable.dkbuttonrightafter)
        }

        rightVehicleImageButton.setOnClickListener {
            leftVehicleButton.setImageResource(R.drawable.dkbuttonleftafternotchoose)
            rightVehicleButton.setImageResource(R.drawable.dkbuttonrightafter)
        }

        // Focus listeners for the color and plat nomor fields
        colorEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                colorEditText.hint = ""
            } else if (colorEditText.text.isEmpty()) {
                colorEditText.hint = "Enter color"
            }
        }

        platNomorEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                platNomorEditText.hint = ""
            } else if (platNomorEditText.text.isEmpty()) {
                platNomorEditText.hint = "format : XX number YY"
            }
        }

        // Text watchers to monitor changes
        colorEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    colorInputLayout.setBackgroundResource(R.drawable.edittextbkgbefore)
                } else {
                    colorInputLayout.setBackgroundResource(R.drawable.edittextbkg)
                }
            }
        })

        platNomorEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrEmpty()) {
                    platNomorInputLayout.setBackgroundResource(R.drawable.edittextbkgbefore)
                    platNomorInputLayout.error = null // Clear error when empty
                } else {
                    platNomorInputLayout.setBackgroundResource(R.drawable.edittextbkg)
                }
            }
        })
    }

    // Validate color and plat nomor inputs
    private fun isValidInputs(color: String, platNomor: String): Boolean {
        var isValid = true

        if (color.isEmpty()) {
            showErrorMessage("Color is required", R.id.dk_inputcolor)
            isValid = false
        }

        if (platNomor.isEmpty()) {
            showErrorMessage("License plate is required", R.id.lp_inputplatnomor)
            isValid = false
        } else if (!isValidPlatNomor(platNomor)) {
            showErrorMessage("Format: 1-3 letters, space, 1-4 numbers, space, 1-3 letters", R.id.lp_inputplatnomor)
            isValid = false
        }

        return isValid
    }

    // Validate plat nomor format using regex
    private fun isValidPlatNomor(platNomor: String): Boolean {
        val platNomorRegex = "^[A-Za-z]{1,3}\\s[0-9]{1,4}\\s[A-Za-z]{1,3}\$".toRegex()
        return platNomor.matches(platNomorRegex)
    }

    // Show error message and set error on input field
    private fun showErrorMessage(message: String, editTextId: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val inputField = findViewById<EditText>(editTextId)
        inputField.error = message
    }
}

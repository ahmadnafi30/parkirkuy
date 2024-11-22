package com.example.parkirkuy.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parkirkuy.R
import java.text.SimpleDateFormat
import java.util.*

class ReservasiActivity : AppCompatActivity() {

    private lateinit var tanggalReservasiEditText: EditText
    private lateinit var waktuMulaiEditText: EditText
    private lateinit var waktuSelesaiEditText: EditText
    private lateinit var reservasiButton: Button
    private lateinit var daftarReservasiButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasiparkir)

        // Inisialisasi View
        tanggalReservasiEditText = findViewById(R.id.edittanggalpemesanan)
        waktuMulaiEditText = findViewById(R.id.edittextwaktumulai)
        waktuSelesaiEditText = findViewById(R.id.editWaktuSelesai)
        reservasiButton = findViewById(R.id.btn_reservasi)

        // Tambahkan TextWatcher untuk mengubah latar belakang dan hint
        addTextWatcher(tanggalReservasiEditText)
        addTextWatcher(waktuMulaiEditText)
        addTextWatcher(waktuSelesaiEditText)

        // Set tanggal reservasi
        tanggalReservasiEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Set waktu mulai
        waktuMulaiEditText.setOnClickListener {
            showTimePickerDialog(waktuMulaiEditText)
        }

        // Set waktu selesai
        waktuSelesaiEditText.setOnClickListener {
            showTimePickerDialog(waktuSelesaiEditText)
        }

        // Tombol reservasi
        reservasiButton.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun addTextWatcher(editText: EditText) {
        val originalHint = editText.hint.toString() // Simpan hint asli
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    // Jika ada teks, ubah background menjadi putih dan sembunyikan hint
                    editText.setBackgroundColor(Color.WHITE)
                    editText.hint = ""
                } else {
                    // Jika teks kosong, kembalikan ke background abu-abu dan tampilkan hint
                    editText.setBackgroundColor(Color.LTGRAY)
                    editText.hint = originalHint
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    "%02d %s %d",
                    selectedDay,
                    getMonthName(selectedMonth),
                    selectedYear
                )
                tanggalReservasiEditText.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun getMonthName(month: Int): String {
        val months = arrayOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        return months[month]
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                editText.setText(formattedTime)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun validateAndSubmit() {
        val tanggalReservasi = tanggalReservasiEditText.text.toString()
        val waktuMulai = waktuMulaiEditText.text.toString()
        val waktuSelesai = waktuSelesaiEditText.text.toString()

        if (TextUtils.isEmpty(tanggalReservasi) ||
            TextUtils.isEmpty(waktuMulai) ||
            TextUtils.isEmpty(waktuSelesai)
        ) {
            Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isTimeFormatValid(waktuMulai) || !isTimeFormatValid(waktuSelesai)) {
            Toast.makeText(this, "Format waktu tidak valid. Gunakan format HH:mm", Toast.LENGTH_SHORT).show()
            return
        }

        // Proses reservasi jika validasi berhasil
        Toast.makeText(this, "Reservasi berhasil dibuat!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DaftarReservasiActivity::class.java)
        startActivity(intent)
    }

    private fun isTimeFormatValid(time: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }
}

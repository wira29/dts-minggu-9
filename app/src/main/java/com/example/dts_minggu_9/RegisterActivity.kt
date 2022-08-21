package com.example.dts_minggu_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {

    val FILENAME : String = "user.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val email : EditText = findViewById(R.id.input_email)
        val password : EditText = findViewById(R.id.input_password)
        val btnRegister : Button = findViewById(R.id.btn_register)
        val btnLogin : Button = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            if(email.text.isEmpty()){
                email.error = "Email tidak boleh kosong !"
                email.isFocused
                return@setOnClickListener
            } else if (password.text.isEmpty()){
                password.error = "Password tidak boleh kosong !"
                password.isFocused
                return@setOnClickListener
            }

            buatFile(email.text.toString(), password.text.toString())
            Toast.makeText(this, "Berhasil daftar akun", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun buatFile(email : String, password : String) {
        var isiFile : String = "${email};${password}"
        var file : File = File(filesDir, FILENAME)

        var outputStream : FileOutputStream? = null
        try {
            file.createNewFile()
            outputStream = FileOutputStream(file, false)
            outputStream.write(isiFile.toByteArray())

            outputStream.flush()
            outputStream.close()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}
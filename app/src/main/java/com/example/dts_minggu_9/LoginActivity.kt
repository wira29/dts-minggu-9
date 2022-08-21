package com.example.dts_minggu_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.lang.StringBuilder

class LoginActivity : AppCompatActivity() {

    val FILENAME : String = "user.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email : EditText = findViewById(R.id.input_email)
        val password : EditText = findViewById(R.id.input_password)
        val btnLogin : Button = findViewById(R.id.btn_login)
        val btnRegister : Button = findViewById(R.id.btn_register)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            if(email.text.isEmpty()){
                email.error = "Email tidak boleh kosong !"
                email.isFocused
                return@setOnClickListener
            } else if (password.text.isEmpty()){
                password.error = "Password tidak boleh kosong !"
                password.isFocused
                return@setOnClickListener
            }

            val hasil : Array<String> = bacaFile()
            Log.d("TAG", "onCreate: ${hasil}")
            if(hasil[0].equals(email.text.toString()) && hasil[1].equals(password.text.toString())){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Email atau Password anda salah !", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun bacaFile() : Array<String> {
        var sdCard : File = filesDir
        var file : File = File(sdCard, FILENAME)

        if(file.exists()){
            var text : StringBuilder = StringBuilder()
            try{
                var br : BufferedReader = BufferedReader(FileReader(file))
                var line : String? = br?.readLine()

                while(line != null){
                    text.append(line)
                    line = br.readLine()
                }
                br.close()
            }catch (e : IOException){
                println("Error : " + e.message)
            }
            return text.split(';').toTypedArray()
        }
        return arrayOf("", "")
    }
}
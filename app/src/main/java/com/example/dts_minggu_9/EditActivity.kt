package com.example.dts_minggu_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class EditActivity : AppCompatActivity() {
    var judul : String? = null
    lateinit var catatan : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        judul = intent.getStringExtra("judul")
        var txtCatatan : String? = intent.getStringExtra("catatan")

        catatan = findViewById(R.id.input_catatan)
        catatan.text = txtCatatan?.toEditable()

        var btn_simpan = findViewById<Button>(R.id.btn_save)
        btn_simpan.setOnClickListener {
            simpan()
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun simpan() {

//        save catatan
        var inputFile = catatan.text.toString()
        var file : File = File(filesDir, judul + ".txt")

        var outputStream : FileOutputStream? = null
        try {

//            save catatan
            file.createNewFile()
            outputStream = FileOutputStream(file, false)
            outputStream.write(inputFile.toByteArray())

            outputStream.flush()
            outputStream.close()

            Toast.makeText(this, "Berhasil mengedit catatan !", Toast.LENGTH_LONG).show()
            finish()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}
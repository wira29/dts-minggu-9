package com.example.dts_minggu_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class AddActivity : AppCompatActivity() {

    val FILENAME : String = "listfile.txt"
    lateinit var judul : EditText
    lateinit var catatan : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        judul = findViewById(R.id.input_judul)
        catatan = findViewById(R.id.input_catatan)

        var btn_simpan = findViewById<Button>(R.id.btn_save)
        btn_simpan.setOnClickListener {
            simpan()
        }
    }

    fun simpan() {
//        save item
        var fileList : File = File(filesDir, FILENAME)
        var listOutputStream : FileOutputStream? = null

//        save catatan
        var inputFile = catatan.text.toString()
        var file : File = File(filesDir, judul.text.toString() + ".txt")

        var outputStream : FileOutputStream? = null
        try {
//            save item
            fileList.createNewFile()
            listOutputStream = FileOutputStream(fileList, true)
            listOutputStream.write((judul.text.toString() + ".txt,").toByteArray())

//            save catatan
            file.createNewFile()
            outputStream = FileOutputStream(file, true)
            outputStream.write(inputFile.toByteArray())

            listOutputStream.flush()
            outputStream.flush()
            listOutputStream.close()
            outputStream.close()

            Toast.makeText(this, "Berhasil menambahkan catatan !", Toast.LENGTH_LONG).show()
            finish()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}
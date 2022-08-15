package com.example.dts_minggu_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import java.io.*
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {
    var LISTFILE : String = "listfile.txt"
    var filename: String? = null
    lateinit var judul : TextView
    lateinit var catatan : TextView
    lateinit var txtJudul : String
    lateinit var txtCatatan : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        judul = findViewById(R.id.val_judul)
        catatan = findViewById(R.id.val_catatan)

        filename = intent.getStringExtra("data")
        getCatatan()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_delete -> hapusFile()
            R.id.btn_edit -> {
                var intent = Intent(this, EditActivity::class.java)
                intent.putExtra("judul", txtJudul)
                intent.putExtra("catatan", txtCatatan)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    fun hapusFile() {
        var file : File = File(filesDir, filename)
        if(file.exists()){
            file.delete()
            updateListfile()
            Toast.makeText(this, "Berhasil menghapus file !", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun updateListfile() {
        var sdCard : File = filesDir
        var file : File = File(sdCard, LISTFILE)

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

            var tmp : ArrayList<String> = text.split(",") as ArrayList<String>
            tmp.remove(filename)
            var list : StringBuilder = StringBuilder()
            var i = 0;
            for(l : String in tmp) {
                if(i < tmp.size - 1){
                    list.append(l + ",")
                } else {
                    list.append(l)
                }
                i++
            }

            Log.d("TAG UPDATE", "updateListfile: " + list.toString())
            Log.d("TAG UPDATE", "updateListfile: " + tmp.size)

            var listOutputStream : FileOutputStream? = null
            try {
//            save item
                file.createNewFile()
                listOutputStream = FileOutputStream(file, false)
                listOutputStream.write(list.toString().toByteArray())
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCatatan() {
        var sdCard : File = filesDir
        var file : File = File(sdCard, filename)

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

            val tmp = filename?.split(".")
            txtJudul = tmp?.get(0) ?: ""
            txtCatatan = text.toString()
            judul.text = tmp?.get(0) ?: ""
            catatan.text = text.toString()
        }
    }
}
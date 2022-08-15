package com.example.dts_minggu_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.lang.StringBuilder
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    var FILENAME : String = "listfile.txt"
    var listfile : ArrayList<String> = arrayListOf<String>()
    lateinit var arrayAdapter : ArrayAdapter<String>
    lateinit var listview : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getList()

        listview = findViewById<ListView>(R.id.list)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listfile)
        listview.adapter = arrayAdapter

        listview.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", listfile[position])
            startActivity(intent)
        }

    }

    fun getList() {
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
            var list = text.split(",")
            var tmpList = arrayListOf<String>()
            for(a : String in list){
                tmpList.add(a)
                Log.d("TAG", "getList: " + a)
            }
            tmpList.remove("")
            listfile = tmpList
        }
    }

    override fun onResume() {
        super.onResume()

        getList()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listfile)
        listview.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> {
                var intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}
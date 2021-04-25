package com.example.weatherappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class MainActivity : AppCompatActivity() {

    val CITY:String = "DaNang"
    val API:String ="https://api.openweathermap.org/data/2.5/weather?q=$CITY&lang=vi&units=metric&appid=a6f41d947e0542a26580bcd5c3fb90ef"

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
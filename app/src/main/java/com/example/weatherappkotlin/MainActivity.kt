package com.example.weatherappkotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception
import java.net.URL
import java.nio.charset.Charset
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class MainActivity : AppCompatActivity() {

    val CITY: String = "DaNang"
    val API1day: String =
        "https://api.openweathermap.org/data/2.5/weather?q=$CITY&lang=vi&units=metric&appid=a6f41d947e0542a26580bcd5c3fb90ef"
    val API: String = "a6f41d947e0542a26580bcd5c3fb90ef"
    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&lang=vi&units=metric&appid=a6f41d947e0542a26580bcd5c3fb90ef").readText(
                        Charsets.UTF_8
                    )
            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updateAt: Long = jsonObj.getLong("dt")
                val updateAtText =
                    "Update at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updateAt * 1000)
                    )
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min temp: " + main.getString("temp_min" + "°C")
                val tempMax = "Max temp: " + main.getString("temp_max" + "°C")
                val pressure = main.getString("pressure")
                val huminity = main.getString("huminity")
                val sunsrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val andress = jsonObj.getString("name") + "," + sys.getString("country")

                findViewById<TextView>(R.id.andressTxt).text = andress
                findViewById<TextView>(R.id.update_at).text = updateAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.tempMin).text = tempMin
                findViewById<TextView>(R.id.tempMax).text = tempMax
                findViewById<TextView>(R.id.humidity).text = huminity
                findViewById<TextView>(R.id.sunrise).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunsrise * 1000))
                findViewById<TextView>(R.id.sunset).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errortext).text = e.toString()
                findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE

            }
        }
    }
}
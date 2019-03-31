package com.example.pokedexktlio

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokerecycler4.Poke
import com.example.pokerecycler4.Pokedex
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import java.io.IOException
import java.net.URL

class SinglePoke : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val mIntent = intent
        count.text = mIntent.getStringExtra("key")
        Img((mIntent.getStringExtra("key").toInt() + 1).toString())

        if (mIntent.getStringExtra("key").isEmpty()) {
            result.setText("nothin to shog")
        } else {
            FetchPokemonTask().execute((mIntent.getStringExtra("key").toInt() + 1).toString())
        }


    }

    fun Img(Id: String) {
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + Id + ".png")
            .into(img)


    }

    inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String? {
            if (strings.size == 0) {
                return null
            }
            var url: URL? = null
            url = Utils().buildURL(strings[0]) //Esta madre te construye el url que se metera en la poke api
            try {
                return Utils().getResponseFromHttpUrl(url!!)//el json como una string
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null// un null XD

        }

        override fun onPostExecute(s: String?) {

            if (s != null) {
                // get JSONObject from JSON file
                val article = Gson().fromJson(s, Poke::class.java)
                print(article)
                val name = article.forms[0].name.substring(0, 1).toUpperCase() + article.forms[0].name.substring(1)

                var tip =
                    article.types[0].type.name.substring(0, 1).toUpperCase() + article.types[0].type.name.substring(1)

                if (article.types[0].slot == 2) {
                    var tipex =
                        article.types[1].type.name.substring(0, 1).toUpperCase() + article.types[1].type.name.substring(
                            1
                        )
                    tipo1.text = tipex
                    tipo2.text = tip
                } else {
                    tipo1.text = tip
                }
                val shp="Hp "+article.stats[5].base_stat.toString()
                val sspa="Spa "+article.stats[2].base_stat.toString()
                val satk="Atk "+article.stats[4].base_stat.toString()
                val ssdef="Sdef "+article.stats[1].base_stat.toString()
                val sdf="Def "+article.stats[3].base_stat.toString()
                val sspeed="Speed "+article.stats[0].base_stat.toString()
                hp.text=shp
                spa.text=sspa
                atk.text=satk
                sdef.text=ssdef
                def.text=sdf
                speed.text=sspeed
                poke.text = name


            }
        }
    }
}
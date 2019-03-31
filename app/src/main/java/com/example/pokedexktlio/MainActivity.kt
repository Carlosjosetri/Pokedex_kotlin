package com.example.pokedexktlio

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pokedexktlio.Models.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import com.example.pokerecycler4.Pokedex
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_element_pokemon.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchPokemonInit().execute()
        butserach.setOnClickListener { view ->
            val id = labelpoke2.text.toString()
            val intent = Intent(this, SinglePoke::class.java)
            intent.putExtra("key",(id.toInt()-1).toString())

            this.startActivity(intent)

        }

    }




    inner class FetchPokemonInit : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg strings: Void): String? {

            var url: URL? = null
            url = Utils().buildURLInit() //Esta madre te construye el url que se metera en la poke api
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
                initRecycler(s)


            }
        }
    }

    fun initRecycler(s: String?) {
        val article = Gson().fromJson(s, Pokedex::class.java)
        var pokemon: MutableList<Pokemon> = MutableList(151) { i ->
            Pokemon(i, article.pokemon_entries[i].pokemon_species.name)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = PokemonAdapter(pokemon, this)

        rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}

package com.example.pokedexktlio

import android.content.ClipData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokedexktlio.Models.Pokemon

import kotlinx.android.synthetic.main.list_element_pokemon.view.*
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast





class PokemonAdapter(val items: List<Pokemon>, val context: Context) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    // TODO: Para contar elementos creados
    private var countViews: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_element_pokemon, parent, false)

        /*
         * TODO: Muestra el valor de contador de view creadas solo se hace aqui, para asegurar
         * que solo se asigne el valor aqui
         */
        countViews++
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (position + 1).toString() + ".png")
            .into(holder.ico)

        holder.itemView.setOnClickListener(View.OnClickListener {


            val intent = Intent(holder.itemView.context, SinglePoke::class.java)
            intent.putExtra("key",items[position].id.toString())

            holder.itemView.context.startActivity(intent)
        })
        holder.itemView.setBackgroundColor(23)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var ico: ImageView
        var count=itemView.findViewById<TextView>(R.id.tv_pokemon_id)
        fun bind(item: Pokemon) = with(itemView) {
            val name = item.name.substring(0, 1).toUpperCase() + item.name.substring(1)
            val id = "Pokedex#: " + (item.id + 1).toString()
            val id2 = item.id.toString()
            tv_pokemon_name.text = name
            tv_pokemon_type.text = id
            tv_pokemon_id.text=id2
            ico = img


        }


    }

}
package com.example.pokerecycler4

import com.example.pokedexktlio.PokeModel.Form
import com.example.pokedexktlio.PokeModel.Stats
import com.example.pokedexktlio.PokeModel.Types


data class Poke(


    var forms: List<Form>,
    var types: List<Types>,
    var stats: List<Stats>
)


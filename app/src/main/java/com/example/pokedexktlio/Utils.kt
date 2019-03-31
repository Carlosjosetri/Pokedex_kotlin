package com.example.pokedexktlio

import android.net.Uri

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Scanner

class Utils {

    private val POKE_API_BASE_URL_DEX = "https://pokeapi.co/api/v2/pokedex/"
    private val POKE_API_BASE_URL = "https://pokeapi.co/api/v2/pokemon/"
    private val POKEMON_PATH = "2"


    fun buildURLInit(): URL? {
        val uri = Uri.parse(POKE_API_BASE_URL_DEX)
            .buildUpon()
            .appendPath(POKEMON_PATH)
            .build()
        var url: URL? = null
        try {
            url = URL(uri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return url
    }

    fun buildURL(id: String): URL? {
        val uri = Uri.parse(POKE_API_BASE_URL)
            .buildUpon()
            .appendPath(id)
            .build()
        var url: URL? = null
        try {
            url = URL(uri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return url
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }

}

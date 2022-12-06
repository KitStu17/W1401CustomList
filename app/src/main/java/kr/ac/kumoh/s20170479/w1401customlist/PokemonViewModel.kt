package kr.ac.kumoh.s20170479.w1401customlist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class PokemonViewModel(application: Application) : AndroidViewModel(application) {
    data class Pokemon (var id: Int, var name: String, var firstdebut: String, var type: String, var image: String)

    companion object {
        const val QUEUE_TAG = "PokemonVolleyRequest"
    }

    private val pokemons = ArrayList<Pokemon>()
    private val _list = MutableLiveData<ArrayList<Pokemon>>()
    val list: LiveData<ArrayList<Pokemon>>
        get() = _list

    private var queue: RequestQueue

    init {
        _list.value = pokemons
        queue = Volley.newRequestQueue(getApplication())
    }

    fun requestPokemon() {
        val url = "https://pokemon-20170479-jonghyeon.run.goorm.io/pokemon"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                pokemons.clear()
                parseJson(it)
                _list.value = pokemons
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray){
        for (i in 0 until items.length()){
            val item: JSONObject = items[i] as JSONObject
            val id = item.getInt("id_no")
            val name = item.getString("name")
            val firstdebut = item.getString("firstdebut")
            val type = item.getString("type")
            val image = item.getString("art")

            pokemons.add(Pokemon(id, name, firstdebut, type, image))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}
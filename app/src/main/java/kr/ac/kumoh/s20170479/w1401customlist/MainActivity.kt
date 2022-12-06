package kr.ac.kumoh.s20170479.w1401customlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20170479.w1401customlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: PokemonViewModel
    private val pokeadapter = PokomonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[PokemonViewModel::class.java]

        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = pokeadapter
        }

        model.list.observe(this){
            pokeadapter.notifyItemRangeInserted(0, model.list.value?.size ?: 0)
        }

        model.requestPokemon()
    }

    inner class PokomonAdapter : RecyclerView.Adapter<PokomonAdapter.ViewHolder>(){
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            // val txText = itemView.findViewById<TextView>(android.R.id.text1)
            val txName: TextView = itemView.findViewById(R.id.text1)
            val txFirstdebut: TextView = itemView.findViewById(R.id.text2)
            // val txType: TextView = itemView.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_pokemon, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // holder.txText.text = model.list.value?.get(position).toString()
            holder.txName.text = model.list.value?.get(position)?.name ?: null
            holder.txFirstdebut.text = model.list.value?.get(position)?.firstdebut ?: null
            // holder.txType.text = model.list.value?.get(position)?.type ?: null
        }

        override fun getItemCount() = model.list.value?.size ?: 0
    }
}
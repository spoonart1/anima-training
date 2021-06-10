package com.spoonart.firstlistviewapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spoonart.firstlistviewapp.R
import com.spoonart.firstlistviewapp.entity.Animal

class AnimalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: AnimalClickListener? = null
    private var animals = mutableListOf<Animal>()

    fun setAnimals(animals: MutableList<Animal>) {
        if (this.animals.size > 0) {
            this.animals.clear()
        }
        this.animals = animals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_animal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolder
        vh.bindView(position)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    fun getAnimalId(index: Int): String {
        return animals[index].id
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvLabel: TextView = itemView.findViewById(R.id.tv_label)
        private val tvDetails: TextView = itemView.findViewById(R.id.tv_details)

        fun bindView(position: Int) {
            val animal = animals[position]
            tvLabel.text = animal.name
            tvDetails.text = animal.note

            itemView.setOnClickListener {
                listener?.onItemClick(position)
            }
        }
    }

    interface AnimalClickListener {
        fun onItemClick(position: Int)
    }
}
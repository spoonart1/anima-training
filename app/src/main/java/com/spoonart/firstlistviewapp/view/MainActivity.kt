package com.spoonart.firstlistviewapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spoonart.firstlistviewapp.R
import com.spoonart.firstlistviewapp.adapter.AnimalAdapter
import com.spoonart.firstlistviewapp.database.AnimalFakeDatabase
import com.spoonart.firstlistviewapp.database.AnimalRepository
import com.spoonart.firstlistviewapp.database.AnimalRepositoryImpl
import com.spoonart.firstlistviewapp.database.DatabaseListener
import com.spoonart.firstlistviewapp.entity.Animal

class MainActivity : AppCompatActivity(), DatabaseListener {

    private lateinit var animalListView: RecyclerView
    private lateinit var animalAdapter: AnimalAdapter
    private var animalRepository: AnimalRepository = AnimalRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Animals"

        animalListView = findViewById(R.id.recyclerview)
        animalListView.layoutManager = LinearLayoutManager(this)

        setData(animalRepository.getAnimals())


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            CrudActivity.start(this, mode = CrudActivity.MODE_CREATE)
        }
        animalRepository.setDatabaseListener(this)
    }

    private fun setData(animals: List<Animal>) {
        animalAdapter = AnimalAdapter()
        animalAdapter.setAnimals(animals.toMutableList())
        animalListView.adapter = animalAdapter

        animalAdapter.listener = object : AnimalAdapter.AnimalClickListener {
            override fun onItemClick(position: Int) {
                DetailActivity.start(this@MainActivity, animalAdapter.getAnimalId(position))
            }
        }
    }

    private fun refreshAdapter(){
        setData(animalRepository.getAnimals())
    }

    override fun onObjectInserted(inserted: Animal, size: Int) {
        println("INSERT new animal with name: ${inserted.name} and note: ${inserted.note}, id: ${inserted.id}")
        refreshAdapter()
    }

    override fun onObjectRemoved(removed: Animal, size: Int) {
        println("REMOVE animal with name: ${removed.name} and note: ${removed.note}, id: ${removed.id}")
        refreshAdapter()
    }

    override fun onObjectUpdated(updated: Animal, affected: Int) {
        println("UPDATE animal with name: ${updated.name} and note: ${updated.note}, id: ${updated.id}")
        refreshAdapter()
    }

}
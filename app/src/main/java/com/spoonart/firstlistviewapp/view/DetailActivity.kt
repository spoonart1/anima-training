package com.spoonart.firstlistviewapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.spoonart.firstlistviewapp.R
import com.spoonart.firstlistviewapp.database.AnimalRepository
import com.spoonart.firstlistviewapp.database.AnimalRepositoryImpl
import com.spoonart.firstlistviewapp.entity.Animal

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ID = "animal-ID"
        fun start(activity: Activity, animalId: String) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(KEY_ID, animalId)
            }
            activity.startActivity(intent)
        }
    }

    private val tvName: TextView by lazy {
        findViewById(R.id.textView2)
    }

    private val tvDetails: TextView by lazy {
        findViewById(R.id.textView3)
    }

    private val animalRepository: AnimalRepository = AnimalRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val animal = getAnimal()
        if (animal != null) {
            tvName.text = "My Animal is ${animal.name}"
            tvDetails.text = animal.note
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Animal Profile"
    }

    private fun getAnimal(): Animal? {
        val id = intent?.getStringExtra(KEY_ID)
        if (id != null) {
            return animalRepository.getAnimalBy(id)
        }
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
package com.spoonart.firstlistviewapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
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

    private val repository: AnimalRepository = AnimalRepositoryImpl()
    private var currentAnimal : Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        currentAnimal = getAnimal()
        if (currentAnimal != null) {
            tvName.text = "My Animal is ${currentAnimal!!.name}"
            tvDetails.text = currentAnimal!!.note
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Animal Profile"
    }

    private fun getAnimal(): Animal? {
        val id = intent?.getStringExtra(KEY_ID)
        if (id != null) {
            return repository.getAnimalBy(id)
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        else if (item.itemId == R.id.opt_delete){
            currentAnimal?.let {
                removeData(it)
                toast("${it.name} has been removed")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun removeData(animal: Animal){
        repository.delete(animal)
    }

}
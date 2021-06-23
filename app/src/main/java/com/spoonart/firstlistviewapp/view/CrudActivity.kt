package com.spoonart.firstlistviewapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.spoonart.firstlistviewapp.R
import com.spoonart.firstlistviewapp.database.AnimalRepository
import com.spoonart.firstlistviewapp.database.AnimalRepositoryImpl
import com.spoonart.firstlistviewapp.entity.Animal

class CrudActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDesc: EditText
    private lateinit var container: ViewGroup
    private lateinit var saveBtn: Button

    private lateinit var repository: AnimalRepository
    private var currentAnimal: Animal? = null

    private var mode: Int = MODE_CREATE

    companion object {

        const val MODE_EDIT = 1
        const val MODE_CREATE = 2

        fun start(context: Context, mode: Int, animalId: String? = null) {
            val intent = Intent(context, CrudActivity::class.java)
            intent.putExtra("mode", mode)
            intent.putExtra("animal_id", animalId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        repository = AnimalRepositoryImpl()
        container = findViewById(R.id.container)
        etName = findViewById(R.id.et_name)
        etDesc = findViewById(R.id.et_desc)
        saveBtn = findViewById(R.id.btn_save)

        setViewMode()

        saveBtn.setOnClickListener {
            saveData(etName.text, etDesc.text)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home || item.itemId == android.R.id.home) {
            promptExit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun promptExit() {
        if (shouldPromptExistDialog()) {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("You have unsaved data, discard anyway?")
                .setPositiveButton("Discard") { dialog, which ->
                    finish()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }else{
            finish()
        }
    }

    private fun setViewMode() {
        mode = intent.getIntExtra("mode", 0)
        val animalId = intent.getStringExtra("animal_id")
        if (mode == 0) {
            throw IllegalArgumentException("mode value cannot be 0, see CrudActivity.start()")
        }

        if (mode == MODE_EDIT) {
            if (animalId.isNullOrEmpty()) {
                throw java.lang.IllegalArgumentException("animal id cannot be null / empty when edit mode")
            }
            currentAnimal = getAnimal(animalId)
            etName.setText(currentAnimal?.name, TextView.BufferType.EDITABLE)
            etDesc.setText(currentAnimal?.note, TextView.BufferType.EDITABLE)
        }
    }

    private fun saveData(name: Editable?, description: Editable?) {
        val validation = validation(name, description)
        if (validation == Validation.Valid) {
            finish()
        } else {
            Snackbar.make(container, validation.message, Snackbar.LENGTH_LONG).show()
        }

        //save data depends on the mode
        if (mode == MODE_CREATE) {
            repository.insertAnimal(
                Animal(
                    name = name!!.toString(),
                    note = description!!.toString()
                )
            )
        } else if (mode == MODE_EDIT) {
            repository.update(currentAnimal!!)
        }
    }

    private fun validation(name: Editable?, description: Editable?): Validation {
        if (name.isNullOrEmpty()) {
            return Validation.InvalidName
        } else if (description.isNullOrEmpty()) {
            return Validation.InvalidDescription
        }
        return Validation.Valid
    }

    private fun getAnimal(id: String): Animal? {
        return repository.getAnimalBy(id)
    }

    private fun shouldPromptExistDialog(): Boolean {
        return !etName.text.isNullOrEmpty() or !etDesc.text.isNullOrEmpty()
    }

    sealed class Validation(var message: String) {
        object InvalidName : Validation("Name cannot be empty")
        object InvalidDescription : Validation("Description cannot be empty")
        object Valid : Validation("All good")
    }
}
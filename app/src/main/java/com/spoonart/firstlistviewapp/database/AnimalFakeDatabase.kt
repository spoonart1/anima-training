package com.spoonart.firstlistviewapp.database

import com.spoonart.firstlistviewapp.entity.Animal

internal class AnimalFakeDatabase {

    companion object {
        val instance by lazy {
            AnimalFakeDatabase()
        }
    }

    private val list: MutableList<Animal>
    private var listener: DatabaseListener? = null

    init {
        list = generate()
    }

    fun insert(animal: Animal): Int {
        val isExist = list.find { it.id == animal.id } != null

        //insert must unique
        if (!isExist) {
            list.add(animal)
        }
        val size = list.size
        listener?.onObjectInserted(animal, size)
        return size
    }

    fun delete(animal: Animal): Int {
        list.remove(animal)
        val size = list.size
        listener?.onObjectRemoved(animal, size)
        return size
    }

    fun update(animal: Animal): Boolean {
        val expected = list.find { it.id == animal.id } ?: return false
        expected.name = animal.name
        expected.note = animal.note
        listener?.onObjectUpdated(expected, 1)
        return true
    }

    fun get(id: String): Animal? {
        return list.find { it.id == id }
    }

    fun getAnimals() : List<Animal>{
        return list.reversed()
    }

    fun setDatabaseListener(listener: DatabaseListener) {
        this.listener = listener
    }

    private fun generate(): MutableList<Animal> {
        return mutableListOf(
            Animal(name = "Ayam", note = "lorem ipsum"),
            Animal(name = "Kuda", note = "lorem ipsum"),
            Animal(name = "Bebek", note = "lorem ipsum"),
            Animal(name = "Angsa", note = "lorem ipsum"),
            Animal(name = "Sapi", note = "lorem ipsum"),
            Animal(name = "Kambing", note = "lorem ipsum"),
            Animal(name = "Ular", note = "lorem ipsum"),
            Animal(name = "Rusa", note = "lorem ipsum"),
            Animal(name = "Domba", note = "lorem ipsum"),
            Animal(name = "Kuda", note = "lorem ipsum"),
            Animal(name = "Onta", note = "lorem ipsum"),
            Animal(name = "Kelinci", note = "lorem ipsum"),
            Animal(name = "Kijang", note = "lorem ipsum"),
            Animal(name = "Musang", note = "lorem ipsum"),
            Animal(name = "Kucing", note = "lorem ipsum"),
            Animal(name = "Anjing", note = "lorem ipsum"),
            Animal(name = "Tikus", note = "lorem ipsum"),
            Animal(name = "Babi", note = "lorem ipsum"),
            Animal(name = "Marmut", note = "lorem ipsum"),
            Animal(name = "Hamster", note = "lorem ipsum"),
            Animal(name = "Jerapah", note = "lorem ipsum"),
            Animal(name = "Kerbau", note = "lorem ipsum"),
            Animal(name = "Koala", note = "lorem ipsum"),
            Animal(name = "Kangguru", note = "lorem ipsum")
        )
    }
}

interface DatabaseListener {

    fun onObjectInserted(inserted: Animal, size: Int)

    fun onObjectRemoved(removed: Animal, size: Int)

    fun onObjectUpdated(updated: Animal, affected: Int)

}
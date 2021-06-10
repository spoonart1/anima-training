package com.spoonart.firstlistviewapp.database

import com.spoonart.firstlistviewapp.entity.Animal


interface AnimalRepository {
    fun getAnimals(): List<Animal>

    fun insertAnimal(animal: Animal): Int

    fun update(animal: Animal): Boolean

    fun delete(animal: Animal): Int

    fun getAnimalBy(id: String): Animal?

    fun setDatabaseListener(listener: DatabaseListener)
}

class AnimalRepositoryImpl : AnimalRepository {
    override fun getAnimals(): List<Animal> {
        return AnimalFakeDatabase.instance.getAnimals()
    }

    override fun insertAnimal(animal: Animal): Int {
        return AnimalFakeDatabase.instance.insert(animal)
    }

    override fun update(animal: Animal): Boolean {
        return AnimalFakeDatabase.instance.update(animal)
    }

    override fun delete(animal: Animal): Int {
        return AnimalFakeDatabase.instance.delete(animal)
    }

    override fun getAnimalBy(id: String): Animal? {
        return AnimalFakeDatabase.instance.get(id)
    }

    override fun setDatabaseListener(listener: DatabaseListener) {
        AnimalFakeDatabase.instance.setDatabaseListener(listener)
    }
}
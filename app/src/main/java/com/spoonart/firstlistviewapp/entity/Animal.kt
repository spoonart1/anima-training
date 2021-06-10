package com.spoonart.firstlistviewapp.entity

import java.util.*

data class Animal(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var note: String
)
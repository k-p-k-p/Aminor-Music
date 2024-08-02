package com.example.aminormusic.model.apidataclasses

data class ApiData(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)
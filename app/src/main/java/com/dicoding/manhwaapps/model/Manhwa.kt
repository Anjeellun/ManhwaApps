package com.dicoding.manhwaapps.model

data class Manhwa (
    val id: String,
    val manhwaName: String,
    val synopsis: String,
    val genre: String,
    val author: String,
    val artist: String,
    val publisher: String,
    val status: String,
    val serialization: String,
    val readUrl: String,
    val reaUrlKor: String,
    val coverUrl: String,
)

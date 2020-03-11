package com.nextus.domain.model

data class Gist(
    val url: String,
    val forksUrl: String,
    val commitsUrl: String,
    val id: String,
    val public: Boolean
)
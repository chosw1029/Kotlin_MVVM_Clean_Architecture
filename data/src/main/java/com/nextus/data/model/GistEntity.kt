package com.nextus.data.model

import com.google.gson.annotations.SerializedName
import com.nextus.domain.model.Gist

data class GistEntity(
    @field:SerializedName("url") val url: String,
    @field:SerializedName("forks_url") val forksUrl: String,
    @field:SerializedName("commits_url") val commitsUrl: String,
    @field:SerializedName("id") val id: String,
    @field:SerializedName("public") val public: Boolean
)

fun GistEntity.mapToDomain() = Gist(
    url, forksUrl, commitsUrl, id, public
)
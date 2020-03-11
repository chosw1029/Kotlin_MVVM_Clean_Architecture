package com.nextus.baseapp.model

import com.nextus.domain.model.Gist

data class GistItem(
    val url: String,
    val forksUrl: String,
    val commitsUrl: String,
    val id: String,
    val public: Boolean
)

fun Gist.mapToPresentation() = GistItem(
    url, forksUrl, commitsUrl, id, public
)
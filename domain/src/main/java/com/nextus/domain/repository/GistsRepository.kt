package com.nextus.domain.repository

import com.nextus.domain.core.Result
import com.nextus.domain.model.Gist

interface GistsRepository {
    suspend fun getGists() : Result<List<Gist>>
}
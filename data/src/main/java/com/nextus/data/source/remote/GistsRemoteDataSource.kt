package com.nextus.data.source.remote

import com.nextus.domain.core.Result
import com.nextus.domain.model.Gist

interface GistsRemoteDataSource {
    suspend fun getGists() : Result<List<Gist>>
}
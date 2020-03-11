package com.nextus.data.repository

import com.nextus.data.source.remote.GistsRemoteDataSource
import com.nextus.domain.core.Result
import com.nextus.domain.model.Gist
import com.nextus.domain.repository.GistsRepository


class GistsRepositoryImpl (
    private val gistsRemoteDataSource: GistsRemoteDataSource
) : GistsRepository {

    override suspend fun getGists(): Result<List<Gist>> {
        return gistsRemoteDataSource.getGists()
    }

}
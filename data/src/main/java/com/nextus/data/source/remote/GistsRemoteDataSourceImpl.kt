package com.nextus.data.source.remote

import com.nextus.data.model.mapToDomain
import com.nextus.data.network.api.GistsApi
import com.nextus.domain.core.Result
import com.nextus.domain.model.Gist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GistsRemoteDataSourceImpl(
    private val gistsApi: GistsApi
) : GistsRemoteDataSource {

    override suspend fun getGists(): Result<List<Gist>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = gistsApi.getGists().execute()

                if(response.isSuccessful) {
                    return@withContext Result.Success(response.body()!!.map { it.mapToDomain() })
                } else {
                    return@withContext Result.Error(Exception("Server Error"))
                }

            } catch (exception: Exception) {
                return@withContext Result.Error(Exception("Network Error"))
            }
        }
    }

}
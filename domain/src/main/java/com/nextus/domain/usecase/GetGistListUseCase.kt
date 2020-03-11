package com.nextus.domain.usecase

import com.nextus.domain.core.Result
import com.nextus.domain.model.Gist
import com.nextus.domain.repository.GistsRepository

class GetGistListUseCase(private val gistsRepository: GistsRepository) {
    suspend operator fun invoke(): Result<List<Gist>> {
        return gistsRepository.getGists()
    }
}
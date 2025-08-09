package com.example.rmcharacters.domain.useCase

import com.example.rmcharacters.domain.model.FilterParameters
import com.example.rmcharacters.domain.repository.AppRepository

class GetCharactersResponseUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(
        filterParameters: FilterParameters
    ) = repository.getCharactersResponse(
        filterParameters
    )
}

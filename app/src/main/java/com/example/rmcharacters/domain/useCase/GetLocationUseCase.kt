package com.example.rmcharacters.domain.useCase

import com.example.rmcharacters.domain.repository.AppRepository

class GetLocationUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(locationIds: List<Int>) = repository.getLocations(locationIds)
}
package com.example.rmcharacters.domain.useCase

import com.example.rmcharacters.domain.repository.AppRepository

class GetNextPageUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke() = repository.getNextPage()
}
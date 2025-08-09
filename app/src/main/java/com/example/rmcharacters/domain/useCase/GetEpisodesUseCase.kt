package com.example.rmcharacters.domain.useCase

import com.example.rmcharacters.domain.repository.AppRepository

class GetEpisodesUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(episodesIds: List<Int>) = repository.getEpisodes(episodesIds)
}
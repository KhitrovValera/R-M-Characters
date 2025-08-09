package com.example.rmcharacters.domain.useCase

data class GetCharacterDetail(
    val getCharacterUseCase: GetCharacterUseCase,
    val getLocationUseCase: GetLocationUseCase,
    val getEpisodesUseCase: GetEpisodesUseCase
)

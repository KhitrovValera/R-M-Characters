package com.example.rmcharacters.domain.useCase

data class GetCharactersUseCase(
    val getCharactersResponseUseCase: GetCharactersResponseUseCase,
    val getNextPageUseCase: GetNextPageUseCase
)
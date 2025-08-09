package com.example.rmcharacters.domain.useCase

import com.example.rmcharacters.domain.common.Resource
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.repository.AppRepository

class GetCharacterUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: Int): Resource<Character> {
        return repository.getCharacter(id)
    }
}
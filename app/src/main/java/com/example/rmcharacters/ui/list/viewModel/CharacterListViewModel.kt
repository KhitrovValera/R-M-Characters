package com.example.rmcharacters.ui.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmcharacters.domain.common.Resource
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.FilterParameters
import com.example.rmcharacters.domain.useCase.GetCharactersUseCase
import com.example.rmcharacters.ui.mapper.ErrorMapper
import com.example.rmcharacters.ui.model.UiInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val errorMapper: ErrorMapper
): ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        loadCharacters(FilterParameters())
    }

    fun loadCharacters(
        filterParameters: FilterParameters
    ) {
        viewModelScope.launch {
            _state.value = State.Loading

            val result = getCharactersUseCase.getCharactersResponseUseCase(
                filterParameters
            )

            _state.value =  when (result) {
                is Resource.Success -> {
                    State.Content(
                        characters = result.data.results.distinctBy { it.id },
                        error = null
                    )
                }
                is Resource.PartialSuccess -> {
                    State.Content(
                        characters = result.data.results.distinctBy { it.id },
                        error = errorMapper.map(result.error)
                    )
                }
                is Resource.Error -> {
                    State.Error(errorMapper.map(result.error))
                }
                Resource.Empty -> {
                    State.Content(
                        characters = emptyList(),
                        error = null,
                        isEmpty = true
                    )
                }
            }
        }
    }

    fun loadMoreCharacters() {
        val currentState = _state.value

        if (currentState is State.Content && !currentState.isLoadingMore) {
            _state.value = currentState.copy(isLoadingMore = true)

            viewModelScope.launch {
                delay(1000L)
                val result = getCharactersUseCase.getNextPageUseCase()

                _state.value = when (result) {
                    is Resource.Success -> {
                        val mergedCharacters = (currentState.characters + result.data.results)
                            .distinctBy { it.id }

                        currentState.copy(
                            characters = mergedCharacters,
                            isLoadingMore = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        currentState.copy(
                            isLoadingMore = false,
                            error = errorMapper.map(result.error)
                        )
                    }
                    Resource.Empty -> {
                        currentState.copy(
                            isLoadingMore = false,
                            error = null,
                            isEmpty = true
                        )
                    }
                    is Resource.PartialSuccess -> {
                        val mergedCharacters = (currentState.characters + result.data.results)
                            .distinctBy { it.id }

                        currentState.copy(
                            characters = mergedCharacters,
                            isLoadingMore = false,
                            error = errorMapper.map(result.error)
                        )
                    }
                }
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Content(
            val characters: List<Character>,
            val error: UiInfoState? = null,
            val isLoadingMore: Boolean = false,
            val isEmpty: Boolean = false
        ) : State
        data class Error(
            val error: UiInfoState
        ) : State
    }
}
package com.example.rmcharacters.ui.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmcharacters.domain.common.Resource
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.Location
import com.example.rmcharacters.domain.useCase.GetCharacterDetail
import com.example.rmcharacters.ui.mapper.ErrorMapper
import com.example.rmcharacters.ui.model.UiInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: GetCharacterDetail,
    private val errorMapper: ErrorMapper
): ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    fun loadCharacter(id: Int) {
        _state.value = State.Loading
        viewModelScope.launch {
            val characterResult = getCharacterDetail.getCharacterUseCase(id)

            _state.value = when (characterResult) {
                is Resource.Error -> State.Error(errorMapper.map(characterResult.error))
                Resource.Empty -> State.Loading
                is Resource.PartialSuccess -> {
                    val character = characterResult.data
                    val charState = CharacterState(character, errorMapper.map(characterResult.error))
                    State.Content(charState, LocationState.Loading, EpisodesState.Loading)
                }
                is Resource.Success -> {
                    val character = characterResult.data
                    val charState = CharacterState(character, null)
                    State.Content(charState, LocationState.Loading, EpisodesState.Loading)
                }
            }

            val currentState = _state.value as? State.Content ?: return@launch
            val character = currentState.characterState.character
            val locationList = listOf(character.originId, character.lastLocationId)
            loadLocations(locationList)
            loadEpisodes(currentState.characterState.character.episodeIdList)
        }
    }

    private fun loadLocations(locationIds: List<Int>) {
        viewModelScope.launch {
            val result = getCharacterDetail.getLocationUseCase(locationIds)
            val currentState = _state.value as? State.Content ?: return@launch
            val newLocationState = when (result) {
                is Resource.Error -> {
                    LocationState.Loaded(emptyList(), errorMapper.map(result.error))
                }
                is Resource.PartialSuccess -> {
                    LocationState.Loaded(result.data, errorMapper.map(result.error))
                }
                is Resource.Success -> {
                    LocationState.Loaded(result.data)
                }
                Resource.Empty -> LocationState.Loaded(emptyList())
            }
            _state.value = currentState.copy(locationState = newLocationState)
        }
    }

    private fun loadEpisodes(episodeIds: List<Int>) {
        viewModelScope.launch {
            val result = getCharacterDetail.getEpisodesUseCase(episodeIds)
            val currentState = _state.value as? State.Content ?: return@launch
            val newEpisodesState = when (result) {
                is Resource.Error -> {
                    EpisodesState.Loaded(emptyList(), errorMapper.map(result.error))
                }
                is Resource.PartialSuccess -> {
                    EpisodesState.Loaded(result.data, errorMapper.map(result.error))
                }
                is Resource.Success -> {
                    EpisodesState.Loaded(result.data)
                }
                Resource.Empty -> EpisodesState.Loaded(emptyList())
            }
            _state.value = currentState.copy(episodesState = newEpisodesState)
        }
    }

    sealed interface State {
        object Loading : State
        data class Content(
            val characterState: CharacterState,
            val locationState: LocationState,
            val episodesState: EpisodesState
        ) : State
        data class Error(
            val error: UiInfoState
        ) : State
    }
    data class CharacterState(
        val character: Character,
        val error: UiInfoState? = null
    )

    sealed interface LocationState {
        object Loading : LocationState
        data class Loaded(
            val location: List<Location?>,
            val error: UiInfoState? = null
        ) : LocationState
    }

    sealed interface EpisodesState {
        object Loading : EpisodesState
        data class Loaded(
            val episodeList: List<Episode?>,
            val error: UiInfoState? = null
        ) : EpisodesState
    }
}
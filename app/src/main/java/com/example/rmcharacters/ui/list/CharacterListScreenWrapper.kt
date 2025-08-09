package com.example.rmcharacters.ui.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.FilterParameters
import com.example.rmcharacters.ui.list.elements.CharacterScreen
import com.example.rmcharacters.ui.list.elements.FilterSheet
import com.example.rmcharacters.ui.list.viewModel.CharacterListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreenWrapper(
    viewModel: CharacterListViewModel,
    onCharacterClick: (Character) -> Unit
) {
    val listScreenState by viewModel.state.collectAsState()
    var currentFilters by remember { mutableStateOf(FilterParameters()) }
    var showFilterSheet by remember { mutableStateOf(false) }

    val isRefreshing = listScreenState is CharacterListViewModel.State.Loading
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.loadCharacters(currentFilters) }
    ) {
        CharacterScreen(
            state = listScreenState,
            onFilterClick = { showFilterSheet = true },
            onCharacterClick = onCharacterClick,
            onLoadMoreClick = { viewModel.loadMoreCharacters() }
        )
    }

    if (showFilterSheet) {
        BackHandler(enabled = true) {
            showFilterSheet = false
        }
        FilterSheet(
            initialFilters = currentFilters,
            onApplyFilters = { appliedFilters ->
                showFilterSheet = false
                currentFilters = appliedFilters
                viewModel.loadCharacters(appliedFilters)
            },
            onDismiss = { showFilterSheet = false }
        )
    }
}


package com.example.rmcharacters.ui.list.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.R
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.ui.list.viewModel.CharacterListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    state: CharacterListViewModel.State,
    onFilterClick: () -> Unit,
    onCharacterClick: (Character) -> Unit,
    onLoadMoreClick: () -> Unit
) {
    var showErrorDialog by remember { mutableStateOf(false) }

    val errorInfo by remember(state) {
        derivedStateOf {
            when (state) {
                is CharacterListViewModel.State.Content -> state.error
                is CharacterListViewModel.State.Error -> state.error
                else -> null
            }
        }
    }

    if (showErrorDialog) {
        errorInfo?.let {
            ErrorDialog(
                errorInfo = it,
                onDismiss = { showErrorDialog = false }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    ) {
        Column {
            StatusAndFilterBar(
                isError = errorInfo != null,
                onFilterClick = onFilterClick,
                onErrorClick = { showErrorDialog = true }
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                when (state) {
                    is CharacterListViewModel.State.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.wait_its_not_too_long_i_guess),
                                Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    is CharacterListViewModel.State.Content -> {
                        CharacterList(
                            state.characters,
                            onCharacterClick,
                            isLoadingMore = state.isLoadingMore,
                            isEnded = state.isEmpty,
                            onLoadMoreClick = {
                                onLoadMoreClick()
                            }
                        )
                    }

                    is CharacterListViewModel.State.Error -> {
                        Text(
                            text = stringResource(R.string.oh_noting_to_see_here),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
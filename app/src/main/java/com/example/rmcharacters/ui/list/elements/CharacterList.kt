package com.example.rmcharacters.ui.list.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.domain.model.Character

@Composable
fun CharacterList(
    characterList: List<Character>,
    onCharacterClick: (Character) -> Unit,
    isLoadingMore: Boolean,
    isEnded: Boolean,
    onLoadMoreClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(characterList) { _, character ->
            CharacterCard(character) { onCharacterClick(character) }
        }
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            PaginationFooter(
                isLoadingMore,
                isEnded,
                onLoadMoreClick
            )
        }
    }
}
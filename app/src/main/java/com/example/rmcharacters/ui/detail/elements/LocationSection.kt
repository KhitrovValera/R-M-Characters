package com.example.rmcharacters.ui.detail.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.ui.detail.viewModel.CharacterDetailViewModel

@Composable
fun LocationDetailsSection(
    character: Character,
    locationState: CharacterDetailViewModel.LocationState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Locations",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            when (locationState) {
                is CharacterDetailViewModel.LocationState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is CharacterDetailViewModel.LocationState.Loaded -> {
                    val loadedLocationsMap =
                        locationState.location.filterNotNull().associateBy { it.id }
                    LocationCard(
                        title = "Origin",
                        characterLocationName = character.originName,
                        loadedLocation = character.originId.let { loadedLocationsMap[it] }
                    )
                    Spacer(Modifier.height(16.dp))
                    LocationCard(
                        title = "Last known location",
                        characterLocationName = character.lastLocationName,
                        loadedLocation = character.lastLocationId.let { loadedLocationsMap[it] }
                    )
                }
            }
        }
    }
}
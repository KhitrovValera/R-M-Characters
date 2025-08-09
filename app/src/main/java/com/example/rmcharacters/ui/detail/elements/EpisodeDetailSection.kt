package com.example.rmcharacters.ui.detail.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.R
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.ui.detail.viewModel.CharacterDetailViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodesDetailsSection(
    character: Character,
    episodesState: CharacterDetailViewModel.EpisodesState
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
                stringResource(R.string.episodes),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            when (episodesState) {
                is CharacterDetailViewModel.EpisodesState.Loading -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                is CharacterDetailViewModel.EpisodesState.Loaded -> {
                    val loadedEpisodesMap =
                        episodesState.episodeList.filterNotNull().associateBy { it.id }
                    val characterEpisodeIds = character.episodeIdList.toSet()
                    val episodesWithFullInfo = mutableListOf<Episode>()
                    val missingEpisodeIds = mutableListOf<Int>()

                    characterEpisodeIds.forEach { charEpId ->
                        loadedEpisodesMap[charEpId]?.let {
                            episodesWithFullInfo.add(it)
                        } ?: missingEpisodeIds.add(charEpId)
                    }
                    episodesWithFullInfo.sortBy { it.id }
                    missingEpisodeIds.sort()

                    if (episodesWithFullInfo.isNotEmpty()) {
                        Text(
                            stringResource(R.string.participation_in_episodes),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            episodesWithFullInfo.forEach { ep ->
                                EpisodeChip(episode = ep)
                            }
                        }
                    }
                    if (missingEpisodeIds.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp.takeIf { episodesWithFullInfo.isNotEmpty() }
                            ?: 0.dp))
                        Text(
                            stringResource(R.string.episodes_id_only),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            missingEpisodeIds.forEach { episodeId -> EpisodeIdChip(episodeId = episodeId) }
                        }
                    }
                    if (episodesWithFullInfo.isEmpty() && missingEpisodeIds.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text(
                                stringResource(R.string.no_information_about_participation_in_episodes),
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
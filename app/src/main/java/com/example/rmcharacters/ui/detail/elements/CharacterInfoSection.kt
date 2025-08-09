package com.example.rmcharacters.ui.detail.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.example.rmcharacters.R
import com.example.rmcharacters.domain.model.Character

@Composable
fun CharacterInfoSection(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            InfoBlock(
                title = "Вид",
                value = character.species,
                icon = rememberVectorPainter(image = Icons.Default.Person),
                contentDescription = "Вид: ${character.species}"
            )
            InfoBlock(
                title = "Тип",
                value = character.type,
                icon = painterResource(id = R.drawable.ic_type),
                contentDescription = "Тип: ${character.type}"
            )
            InfoBlock(
                title = "Пол",
                value = character.gender,
                icon = painterResource(id = R.drawable.ic_gender),
                contentDescription = "Пол: ${character.gender}"
            )
        }
    }
}
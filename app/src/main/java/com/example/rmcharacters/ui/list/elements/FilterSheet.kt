package com.example.rmcharacters.ui.list.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.domain.model.FilterParameters

@Composable
fun FilterSheet(
    initialFilters: FilterParameters,
    onApplyFilters: (FilterParameters) -> Unit,
    onDismiss: () -> Unit
) {
    var nameInput by remember { mutableStateOf(initialFilters.name ?: "") }
    var selectedName by remember { mutableStateOf(initialFilters.name) }

    var selectedStatus by remember { mutableStateOf(initialFilters.status) }

    var selectedGender by remember { mutableStateOf(initialFilters.gender) }

    var speciesInput by remember { mutableStateOf(initialFilters.species ?: "") }
    var selectedSpecies by remember { mutableStateOf(initialFilters.species) }

    var typeInput by remember { mutableStateOf(initialFilters.type ?: "") }
    var selectedType by remember { mutableStateOf(initialFilters.type) }

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Filter settings",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FilterInputSection(
                    title = "Name",
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    onOptionSelected = { selectedName = it },
                )

                FilterSection(
                    title = "Status",
                    options = listOf("Alive", "Dead", "Unknown"),
                    selectedOption = selectedStatus,
                    onOptionSelected = { selectedStatus = it }
                )

                FilterSection(
                    title = "Gender",
                    options = listOf("Female", "Male", "Genderless", "Unknown"),
                    selectedOption = selectedGender,
                    onOptionSelected = { selectedGender = it }
                )

                FilterInputSection(
                    title = "Species",
                    value = speciesInput,
                    onValueChange = { speciesInput = it },
                    onOptionSelected = { selectedSpecies = it }
                )

                FilterInputSection(
                    title = "Type",
                    value = typeInput,
                    onValueChange = { typeInput = it },
                    onOptionSelected = { selectedType = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val newFilters = FilterParameters(
                                name = selectedName,
                                status = selectedStatus,
                                species = selectedSpecies,
                                gender = selectedGender,
                                type = selectedType
                            )
                            onApplyFilters(newFilters)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Apply", color = Color.White)
                    }
                }
            }
        }
    }
}

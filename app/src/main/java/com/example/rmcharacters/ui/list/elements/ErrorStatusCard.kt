package com.example.rmcharacters.ui.list.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorStatusCard(
    isError: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val clickModifier = if (isError) {
        Modifier
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(28.dp))
    } else {
        Modifier
    }

    Card(
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .then(clickModifier)
                .padding(horizontal = 12.dp)
        ) {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Something went boom",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Something went boom...",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "All systems nominal",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
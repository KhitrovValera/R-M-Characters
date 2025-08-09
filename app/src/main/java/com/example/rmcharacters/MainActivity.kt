package com.example.rmcharacters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.rmcharacters.ui.detail.viewModel.CharacterDetailViewModel
import com.example.rmcharacters.ui.list.viewModel.CharacterListViewModel
import com.example.rmcharacters.ui.navigator.AppNavigator
import com.example.rmcharacters.ui.theme.RMCharactersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RMCharactersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigator(
                        characterListViewModel,
                        characterDetailViewModel,
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }
}
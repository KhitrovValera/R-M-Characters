package com.example.rmcharacters.ui.navigator

import CharacterDetailScreenWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmcharacters.ui.detail.viewModel.CharacterDetailViewModel
import com.example.rmcharacters.ui.list.CharacterListScreenWrapper
import com.example.rmcharacters.ui.list.viewModel.CharacterListViewModel

@Composable
fun AppNavigator(
    characterListViewModel: CharacterListViewModel,
    characterDetailViewModel: CharacterDetailViewModel,
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "character_list",
        modifier
    ) {
        composable("character_list") {
            CharacterListScreenWrapper(
                viewModel = characterListViewModel,
                onCharacterClick = { character ->
                    navController.navigate("character_detail/${character.id}")
                }
            )
        }

        composable(
            route = "character_detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable

            LaunchedEffect(characterId) {
                characterDetailViewModel.loadCharacter(characterId)
            }

            CharacterDetailScreenWrapper(
                characterId = characterId,
                viewModel = characterDetailViewModel
            )
        }

    }

}
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rmcharacters.R
import com.example.rmcharacters.ui.detail.elements.CharacterDetailContent
import com.example.rmcharacters.ui.detail.viewModel.CharacterDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreenWrapper(
    characterId: Int,
    viewModel: CharacterDetailViewModel
) {
    val fullState by viewModel.state.collectAsState()

    val isRefreshing = fullState is CharacterDetailViewModel.State.Loading
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.loadCharacter(characterId) }
    ) {
        when (val currentFullState = fullState) {
            is CharacterDetailViewModel.State.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.wait_its_not_too_long_i_guess),
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is CharacterDetailViewModel.State.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Loading error: ${currentFullState.error.title}",
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is CharacterDetailViewModel.State.Content -> {
                CharacterDetailContent(
                    characterState = currentFullState.characterState,
                    locationState = currentFullState.locationState,
                    episodesState = currentFullState.episodesState
                )
            }
        }
    }
}
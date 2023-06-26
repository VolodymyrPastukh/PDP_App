package pastukh.vova.appcompose.ui.screen.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pastukh.vova.appcompose.ui.components.Action
import pastukh.vova.appcompose.ui.components.ErrorView
import pastukh.vova.appcompose.ui.components.LoadingView
import pastukh.vova.appcompose.ui.components.RecipeListItemView
import pastukh.vova.baseui.R
import pastukh.vova.baseui.viewModel.recipes.RecipesViewState

@Composable
fun RecipesContent(
    state: State<RecipesViewState>,
    onRecipeDetails: (String) -> Unit,
    onDownload: (String) -> Unit,
    onReload: () -> Unit,
    onBack: () -> Unit,
) {
    with(state.value) {
        when (this) {
            is RecipesViewState.Data -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    items(data.size, key = { data[it].id }) {
                        if (it == 0) Spacer(modifier = Modifier.height(8.dp))
                        RecipeListItemView(
                            recipe = data[it],
                            onRecipeDetails = onRecipeDetails,
                            onDownload = onDownload
                        )
                        if (it != data.size) Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            is RecipesViewState.Error ->
                ErrorView(
                    message = message ?: stringResource(id = R.string.error_unknown),
                    actionPrimary = { mod ->
                        Action(
                            title = stringResource(id = R.string.button_try_again),
                            elevation = 8.dp,
                            onClick = onReload,
                            modifier = mod
                        )
                    },
                    actionSecondary = { mod ->
                        Action(
                            title = stringResource(id = R.string.button_cancel),
                            colorBackground = MaterialTheme.colorScheme.secondaryContainer,
                            colorBorder = MaterialTheme.colorScheme.secondary,
                            onClick = onBack,
                            modifier = mod
                        )
                    }
                )

            is RecipesViewState.Loading -> LoadingView()
        }
    }
}
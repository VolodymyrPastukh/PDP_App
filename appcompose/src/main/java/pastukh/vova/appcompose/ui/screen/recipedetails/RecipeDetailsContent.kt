package pastukh.vova.appcompose.ui.screen.recipedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pastukh.vova.appcompose.ui.components.Action
import pastukh.vova.appcompose.ui.components.ErrorView
import pastukh.vova.appcompose.ui.components.LoadingView
import pastukh.vova.appcompose.ui.components.NetworkImage
import pastukh.vova.appcompose.ui.components.theme.TextStyles
import pastukh.vova.baseui.R
import pastukh.vova.baseui.entity.RecipeState
import pastukh.vova.baseui.viewModel.recipeDetails.RecipeDetailsViewState

@Composable
fun RecipeDetailsContent(
    state: State<RecipeDetailsViewState>,
    onDownload: () -> Unit,
    onStart: () -> Unit,
    onReload: () -> Unit,
    onBack: () -> Unit,
) {
    with(state.value) {
        when (this) {
            is RecipeDetailsViewState.Data -> {
                Card(
                    shape = CardDefaults.elevatedShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NetworkImage(
                            url = data.image,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = data.title,
                                style = TextStyles.Header2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                            when (data.state) {
                                RecipeState.DOWNLOADING ->
                                    Action(
                                        icon = painterResource(id = R.drawable.ic_download),
                                        colorContent = MaterialTheme.colorScheme.secondary,
                                        onClick = {},
                                        loading = true
                                    )

                                RecipeState.DEFAULT ->
                                    Action(
                                        icon = painterResource(id = R.drawable.ic_download),
                                        colorContent = MaterialTheme.colorScheme.secondary,
                                        onClick = onDownload,
                                    )

                                RecipeState.DOWNLOADED ->
                                    Action(
                                        icon = painterResource(id = R.drawable.ic_play),
                                        colorContent = MaterialTheme.colorScheme.secondary,
                                        onClick = onStart,
                                    )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.recipe_steps_amount,
                                    data.steps.size
                                ),
                                style = TextStyles.Sub1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(
                                    id = R.string.recipe_duration,
                                    data.durationInSec
                                ),
                                style = TextStyles.Sub1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.recipe_country, data.country),
                            style = TextStyles.Body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                        )
                        Text(
                            text = stringResource(
                                id = R.string.recipe_description,
                                data.description
                            ),
                            style = TextStyles.Body2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                        )
                    }
                }

            }

            is RecipeDetailsViewState.Error ->
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

            is RecipeDetailsViewState.NotFound ->
                ErrorView(
                    message = stringResource(id = R.string.error_not_found),
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

            is RecipeDetailsViewState.Loading -> LoadingView()
        }
    }
}
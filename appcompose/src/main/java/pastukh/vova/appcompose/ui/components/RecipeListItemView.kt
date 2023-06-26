package pastukh.vova.appcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pastukh.vova.appcompose.ui.components.theme.AppDarkTheme
import pastukh.vova.appcompose.ui.components.theme.AppLightTheme
import pastukh.vova.appcompose.ui.components.theme.Colors
import pastukh.vova.appcompose.ui.components.theme.TextStyles
import pastukh.vova.baseui.entity.RecipeEntity
import pastukh.vova.baseui.entity.RecipeState

@Composable
fun RecipeListItemView(
    modifier: Modifier = Modifier,
    recipe: RecipeEntity,
    onRecipeDetails: (String) -> Unit,
    onDownload: (String) -> Unit,
) {
    Card(
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .background(color = Colors.transparent)
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onRecipeDetails(recipe.id) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
            NetworkImage(
                url = recipe.image, modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
                    .padding(start = 16.dp)
            ) {
                Column {
                    Text(
                        text = recipe.title ?: "",
                        style = TextStyles.Body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, end = 52.dp),
                    )
                    Text(
                        text = recipe.country ?: "",
                        style = TextStyles.Sub1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                    Text(
                        text = recipe.description ?: "",
                        style = TextStyles.Sub2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
                when (recipe.state) {
                    RecipeState.DOWNLOADING ->
                        LoadingViewMini(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(48.dp)
                                .padding(4.dp)
                        )

                    RecipeState.DEFAULT -> {
                        Icon(
                            painter = painterResource(id = pastukh.vova.baseui.R.drawable.ic_download),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(48.dp)
                                .padding(4.dp)
                                .clickable { onDownload(recipe.id) }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeListItemViewPreviewLight() {
    AppLightTheme {
        RecipeListItemView(
            recipe = RecipeEntity.TEST,
            onRecipeDetails = {},
            onDownload = {}
        )
    }
}

@Preview
@Composable
fun RecipeListItemViewPreviewDark() {
    AppDarkTheme {
        RecipeListItemView(
            recipe = RecipeEntity.TEST,
            onRecipeDetails = {},
            onDownload = {}
        )
    }
}
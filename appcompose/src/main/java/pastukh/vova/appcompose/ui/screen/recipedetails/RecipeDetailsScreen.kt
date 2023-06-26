package pastukh.vova.appcompose.ui.screen.recipedetails

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.getViewModel
import pastukh.vova.baseui.viewModel.recipeDetails.RecipeDetailsViewModel
import pastukh.vova.components.services.ServiceConstants
import pastukh.vova.components.services.loading.LoadingService
import pastukh.vova.components.services.media.LiveRecipeService

@Composable
fun RecipeDetailsScreen(
    arguments: RecipeDetailsScreenArguments,
    onBack: () -> Unit,
    viewModel: RecipeDetailsViewModel = getViewModel(),
) {
    val context = LocalContext.current
    val recipeId by rememberUpdatedState(newValue = checkNotNull(arguments.data?.recipeId))

    LaunchedEffect(Unit) {
        viewModel.getRecipe(recipeId)
    }

    RecipeDetailsContent(
        state = viewModel.state.collectAsState(),
        onDownload = {
            viewModel.storeRecipe()
            context.startService(
                Intent(context, LoadingService::class.java).apply {
                    putExtra(ServiceConstants.EXTRA_RECIPE_URL, recipeId)
                }
            )
        },
        onStart = {
            context.startService(
                Intent(context, LiveRecipeService::class.java).apply {
                    putExtra(ServiceConstants.EXTRA_RECIPE_URL, recipeId)
                }
            )
        },
        onReload = { viewModel.getRecipe(recipeId) },
        onBack = onBack,
    )
}
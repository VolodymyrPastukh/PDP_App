package pastukh.vova.appcompose.ui.screen.recipes

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.getViewModel
import pastukh.vova.baseui.viewModel.recipes.RecipesViewModel
import pastukh.vova.components.services.ServiceConstants
import pastukh.vova.components.services.loading.LoadingService

@Composable
fun RecipesScreen(
    arguments: RecipesScreenArguments,
    onRecipeDetails: (Int) -> Unit,
    viewModel: RecipesViewModel = getViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getRecipes()
    }

    RecipesContent(
        state = viewModel.state.collectAsState(),
        onRecipeDetails = onRecipeDetails,
        onDownload = {
            context.startService(
                Intent(context, LoadingService::class.java).apply {
                    putExtra(ServiceConstants.EXTRA_RECIPE_URL, it)
                }
            )
            viewModel.storeRecipe(it)
        },
        onReload = { viewModel.getRecipes() },
        onBack = { viewModel.getRecipes() }
    )
}
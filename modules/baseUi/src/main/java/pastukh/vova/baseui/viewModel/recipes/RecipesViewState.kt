package pastukh.vova.baseui.viewModel.recipes

import pastukh.vova.baseui.entity.RecipeEntity

sealed class RecipesViewState {
    data class Data(val data: List<RecipeEntity>) : RecipesViewState()
    data class Error(val message: String? = null) : RecipesViewState()
    object Loading : RecipesViewState()
}

suspend inline fun RecipesViewState.onState(
    crossinline onData: suspend (List<RecipeEntity>) -> Unit,
    crossinline onElse: () -> Unit = {},
) {
    when (this) {
        is RecipesViewState.Data -> onData(data)
        else -> onElse()
    }
}
package pastukh.vova.baseui.viewModel.recipeDetails

import pastukh.vova.baseui.entity.RecipeEntity

sealed class RecipeDetailsViewState {
    data class Data(val data: RecipeEntity) : RecipeDetailsViewState()
    data class Error(val message: String? = null) : RecipeDetailsViewState()
    object NotFound : RecipeDetailsViewState()
    object Loading : RecipeDetailsViewState()
}

suspend inline fun RecipeDetailsViewState.onState(
    crossinline onData: suspend (RecipeEntity) -> Unit,
    crossinline onElse: () -> Unit = {},
) {
    when (this) {
        is RecipeDetailsViewState.Data -> onData(data)
        else -> onElse()
    }
}
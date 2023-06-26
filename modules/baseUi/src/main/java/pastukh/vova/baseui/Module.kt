package pastukh.vova.baseui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pastukh.vova.baseui.viewModel.recipeDetails.RecipeDetailsViewModel
import pastukh.vova.baseui.viewModel.recipes.RecipesViewModel

val moduleBaseUi = module {
    viewModel { RecipesViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}
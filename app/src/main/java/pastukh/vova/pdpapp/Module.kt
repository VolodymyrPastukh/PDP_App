package pastukh.vova.pdpapp

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pastukh.vova.pdpapp.ui.screen.recipedetails.RecipeDetailsViewModel
import pastukh.vova.pdpapp.ui.screen.recipes.RecipesViewModel

val moduleApp = module {
    viewModel { RecipesViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}
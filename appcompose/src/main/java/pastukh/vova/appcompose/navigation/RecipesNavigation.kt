package pastukh.vova.appcompose.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import pastukh.vova.appcompose.ui.screen.recipedetails.RecipeDetailsScreen
import pastukh.vova.appcompose.ui.screen.recipedetails.RecipeDetailsScreenArguments
import pastukh.vova.appcompose.ui.screen.recipes.RecipesScreen
import pastukh.vova.appcompose.ui.screen.recipes.RecipesScreenArguments

fun NavGraphBuilder.recipesNavigation(
    route: String,
    navController: NavController,
) {
    navigation(
        startDestination = RecipesScreenArguments.route,
        route = route,
    ) {
        composable(
            route = RecipesScreenArguments.route,
            arguments = RecipesScreenArguments.arguments,
        ) {
            RecipesScreen(
                arguments = RecipesScreenArguments.fromBackStack(it),
                onRecipeDetails = { id ->
                    navController.navigate(
                        RecipeDetailsScreenArguments(
                            RecipeDetailsScreenArguments.Data(id)
                        ).toRoute()
                    )
                },
            )
        }
        composable(
            route = RecipeDetailsScreenArguments.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.pdpapp.com/recipes?id={id}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = RecipeDetailsScreenArguments.arguments,
        ) {
            RecipeDetailsScreen(
                arguments = RecipeDetailsScreenArguments.fromBackStack(it),
                onBack = { navController.navigateUp() },
            )
        }
    }
}

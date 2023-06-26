package pastukh.vova.appcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

private const val SCOPE_RECIPES = "scope_recipes"

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCOPE_RECIPES,
    ) {
        recipesNavigation(
            route = SCOPE_RECIPES,
            navController = navController,
        )
    }
}
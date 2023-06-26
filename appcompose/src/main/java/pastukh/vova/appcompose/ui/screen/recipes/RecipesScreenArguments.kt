package pastukh.vova.appcompose.ui.screen.recipes

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

class RecipesScreenArguments {
    companion object {
        private const val ROUTE_NAME = "recipes"

        const val route = ROUTE_NAME
        val arguments: List<NamedNavArgument> = listOf()

        fun fromBackStack(backStack: NavBackStackEntry): RecipesScreenArguments {
            return RecipesScreenArguments()
        }
    }

    fun toRoute(): String = ROUTE_NAME
}
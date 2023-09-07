package pastukh.vova.appcompose.ui.screen.recipedetails

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson

class RecipeDetailsScreenArguments(val data: Data?) {
    companion object {
        private const val ROUTE_NAME = "recipe_details"
        private const val ARG_DATA_KEY = "recipe_details_data"
        private const val ARG_DATA = "{$ARG_DATA_KEY}"
        private const val DEEP_LINK_KEY_ID = "id"

        const val route = "$ROUTE_NAME/$ARG_DATA"
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(ARG_DATA_KEY) {
                type = NavType.StringType
                nullable = true
            },
        )

        fun fromBackStack(backStack: NavBackStackEntry): RecipeDetailsScreenArguments {
            val arguments = backStack.arguments?.getString(ARG_DATA_KEY)
            val deepLinkRecipeId = backStack.arguments?.getInt(DEEP_LINK_KEY_ID)
            return RecipeDetailsScreenArguments(
                data = deepLinkRecipeId?.let { Data(deepLinkRecipeId) }
                    ?: arguments?.let {
                        Gson().fromJson(Uri.decode(it), Data::class.java)
                    },
            )
        }
    }

    fun toRoute(): String {
        val arguments = Uri.encode(Gson().toJson(data, Data::class.java))
        return "${ROUTE_NAME}/$arguments"
    }

    data class Data(val recipeId: Int)
}
package pastukh.vova.pdpapp.ui.utils.helper

import android.net.Uri
import android.util.Log
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections

object DeepLinkHelper {
    private const val TAG = "DeepLinkHelper"

    private const val PATH_RECIPES = "/recipes"
    private const val PATH_RECIPES_QUERY_PARAM = "id"

    fun navDestination(uri: Uri?): NavDirections? =
        uri?.let {
            it.log()
            null
        }

    fun navDeepLinkRequest(uri: Uri?): NavDeepLinkRequest? =
        uri?.let {
            it.log()
            when (it.path) {
                PATH_RECIPES -> NavDeepLinkRequest(uri, null, null)
                else -> null
            }
        }


    private fun Uri.log() {
        Log.i(TAG, "uri=$this")
        Log.i(TAG, "scheme=$scheme")
        Log.i(TAG, "host=$host")
        Log.i(TAG, "path=$path")
        Log.i(TAG, "parameter=${getQueryParameter("recipe")}")
    }
}
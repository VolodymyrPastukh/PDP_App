package pastukh.vova.pdpapp.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import pastukh.vova.baseui.R

fun Context.toast(message: String? = null) =
    Toast.makeText(this, message ?: getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()

fun Context.toast(stringId: Int? = null) =
    Toast.makeText(this, getString(stringId ?: R.string.error_unknown), Toast.LENGTH_SHORT).show()

fun Context?.isNetworkAvailable(): Boolean {
    if (this == null) return false

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetwork?.let { nw ->
        connectivityManager.getNetworkCapabilities(nw)?.let { nc ->
            when {
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    } ?: false
}
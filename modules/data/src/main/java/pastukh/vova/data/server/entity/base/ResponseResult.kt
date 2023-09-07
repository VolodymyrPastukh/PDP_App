package pastukh.vova.data.server.entity.base

import android.util.Log


sealed class ResponseResult<out T> {
    companion object {
        private const val TAG = "ResponseResult"
    }

    data class Success<T : Any>(val data: T) : ResponseResult<T>() {
        init { Log.i(TAG, "SUCCESS -> $data") }
    }

    data class ServerError(val error: String?) : ResponseResult<Nothing>()

    data class Error(val error: Exception) : ResponseResult<Nothing>() {
        init { Log.i(TAG, "ERROR -> ${error.message}|${error.cause}") }
    }
}

suspend inline fun <T : Any> ResponseResult<T>.onSuccess(crossinline block: suspend (T) -> Unit) {
    when (this) {
        is ResponseResult.Success<T> -> block(this.data)
        else -> {}
    }
}

fun <T : Any> ResponseResult<T>.getOrNull(): T? =
    when (this) {
        is ResponseResult.Success<T> -> data
        else -> null
    }
